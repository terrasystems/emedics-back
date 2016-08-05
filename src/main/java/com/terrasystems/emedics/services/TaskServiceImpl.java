package com.terrasystems.emedics.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TaskCriteriaDto;
import com.terrasystems.emedics.model.dtoV2.TaskDto;
import com.terrasystems.emedics.model.mapping.TaskMapper;
import com.terrasystems.emedics.model.mapping.UserMapper;
import com.terrasystems.emedics.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Autowired
    EventRepository eventRepository;

    @Override
    public ResponseDto createTask(TaskDto taskDto) {
        Template template = templateRepository.findOne(taskDto.getTemplateDto().getId());
        User patient = userRepository.findOne(taskDto.getPatient().getId());
        User fromUser = userRepository.findOne(taskDto.getFromUser().getId());
        if (patient == null || template == null || fromUser == null) {
            utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        }
        if (utils.isDoctor(fromUser) || utils.isOrg(fromUser) || utils.isStaff(fromUser)) {
            return createTaskForNotPatient(template, patient, fromUser, taskDto.getModel());
        }
        if (utils.isPatient(fromUser)) {
            return createTaskForPatient(template, patient, fromUser, taskDto.getModel());
        }
        return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
    }

    @Override
    public ResponseDto getTaskById(String id) throws IOException {
        Event event = eventRepository.findOne(id);
        if (event == null) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        }
        TaskMapper mapper = TaskMapper.getInstance();
        return utils.generateResponse(true, MessageEnums.MSG_TASK_BY_ID.toString(), mapper.toDto(event));
    }

    @Override
    public ResponseDto editTask(TaskDto taskDto) {
        Event event = eventRepository.findOne(taskDto.getId());
        if (event == null) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        }
        event.setDate(new Date());
        event.setData(taskDto.getModel().toString());
        event.setStatus(StatusEnum.PROCESSED);
        eventRepository.save(event);
        return utils.generateResponse(true, MessageEnums.MSG_TASK_EDITED.toString(), null);
    }

    @Override
    public ResponseDto closeTask(String id) {
        Event event = eventRepository.findOne(id);
        if (event == null) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        }
        event.setStatus(StatusEnum.CLOSED);
        eventRepository.save(event);
        return utils.generateResponse(true, MessageEnums.MSG_TASK_CLOSED.toString(), null);
    }

    @Override
    public ResponseDto multiCreateTask(List<TaskDto> taskDtos) {
        ResponseDto responseDto;
        if (taskDtos.isEmpty()) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        } else {
            for (TaskDto taskDto : taskDtos) {
                responseDto = createTask(taskDto);
                if (!responseDto.getState()) return responseDto;
            }
        }
        return utils.generateResponse(true, MessageEnums.MSG_TASK_CREATED.toString(), null);
    }

    @Override
    public ResponseDto sendTask(TaskDto taskDto) {
        User user = utils.getCurrentUser();
        Event event = eventRepository.findOne(taskDto.getId());
        User recipient = userRepository.findOne(taskDto.getToUser().getId());
        if (event == null || recipient == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        if (utils.isPatient(recipient) && utils.isMedicalForm(event.getTemplate())) return utils.generateResponse(false, MessageEnums.MSG_YOU_CANT_SEND_MED_FORM_TO_PAT.toString(), null);
        if (!user.getReferences().contains(recipient)) return addUserToRef(user, recipient, event, taskDto);
        return sendTaskAction(user, recipient, event, taskDto);
    }

    @Override
    public ResponseDto getAllTasks(TaskCriteriaDto criteria) {
        User current = utils.getCurrentUser();
        List<Event> events = allTasksByCriteria(current, criteria);
        return utils.generateResponse(true, MessageEnums.MSG_ALL_TASKS.toString(), events.stream()
        .map(event -> {
            return mapperTaskList(event);
        }).collect(Collectors.toList()));
    }

    @Override
    public ResponseDto getHistory(TaskCriteriaDto criteria) {
        User current = utils.getCurrentUser();
        List<Event> events = allHistoryByCriteria(current, criteria);
        return utils.generateResponse(true, MessageEnums.MSG_ALL_HISTORY.toString(), events.stream()
                .map(event -> {
                    return mapperTaskList(event);
                }).collect(Collectors.toList()));
    }

    @Override
    public ResponseDto assignTask(TaskDto taskDto) {
        Event event = eventRepository.findOne(taskDto.getId());
        User userFrom = userRepository.findOne(taskDto.getFromUser().getId());
        if (userFrom == null || event == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        event.setStatus(StatusEnum.PROCESSED);
        event.setFromUser(userFrom);
        event.setDate(new Date());
        eventRepository.save(event);
        return utils.generateResponse(true, MessageEnums.MSG_TASK_ASSIGNED.toString(), null);
    }

    private List<Event> allHistoryByCriteria(User current, TaskCriteriaDto criteria) {
        return eventRepository.findAll(Specifications.<Event>where((r, p, b) -> {
            Predicate from = b.equal(r.<User>get("fromUser").get("id"), current.getId());
            Predicate to = b.equal(r.<User>get("toUser").get("id"), current.getId());
            Predicate fromto = b.or(from,to);
            Predicate close =  b.equal(r.<StatusEnum>get("status"), StatusEnum.CLOSED);
            return b.and(fromto, close);
        }).and((r, q, b) -> {
            return getTemplate(criteria, b, r);
        }).and((r, q, b) -> {
            return getPatientName(criteria, b, r);
        }).and((r, q, b) -> {
            return getStatus(criteria, b, r);
        }).and((r, q, b) -> {
            return getFromUser(criteria, b, r);
        }).and((r, q, b) -> {
            return getPeriod(criteria, b, r);
        }));
    }

    private List<Event> allTasksByCriteria(User current, TaskCriteriaDto criteria) {
        return eventRepository.findAll(Specifications.<Event>where((r, p, b) -> {
            Predicate from = b.equal(r.<User>get("fromUser").get("id"), current.getId());
            Predicate statusNew = b.equal(r.<StatusEnum>get("status"), StatusEnum.NEW);
            Predicate statusProcessed = b.equal(r.<StatusEnum>get("status"), StatusEnum.PROCESSED);
            Predicate fromNew = b.and(from, statusNew);
            Predicate toProcessed = b.and(from, statusProcessed);
            return b.or(fromNew, toProcessed);
        }).and((r, q, b) -> {
            return getTemplate(criteria, b, r);
        }).and((r, q, b) -> {
            return getPatientName(criteria, b, r);
        }).and((r, q, b) -> {
            return getStatus(criteria, b, r);
        }).and((r, q, b) -> {
            return getFromUser(criteria, b, r);
        }).and((r, q, b) -> {
            return getPeriod(criteria, b, r);
        }));
    }

    private TaskDto mapperTaskList(Event event) {
        TaskMapper mapper = TaskMapper.getInstance();
        try {
            TaskDto taskDto = mapper.toDto(event);
            return taskDto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Predicate getTemplate(TaskCriteriaDto criteria, CriteriaBuilder b, Root<Event> r) {
        if (criteria.getTemplateName() == null || criteria.getTemplateName().isEmpty()) return  null;
        return b.like(b.lower(r.<Template>get("template").get("name")), "%" + criteria.getTemplateName().toLowerCase() + "%");
    }

    private Predicate getPatientName(TaskCriteriaDto criteria, CriteriaBuilder b, Root<Event> r) {
        if (criteria.getPatientName() == null || criteria.getPatientName().isEmpty()) return  null;
        return b.like(b.lower(r.<User>get("patient").get("name")), "%" + criteria.getPatientName().toLowerCase() + "%");
    }

    private Predicate getFromUser(TaskCriteriaDto criteria, CriteriaBuilder b, Root<Event> r) {
        if (criteria.getFromName() == null || criteria.getFromName().isEmpty()) return  null;
        return b.like(b.lower(r.<User>get("fromUser").get("name")), "%" + criteria.getFromName().toLowerCase() + "%");
    }

    private Predicate getStatus(TaskCriteriaDto criteria, CriteriaBuilder b, Root r) {
        if (criteria.getStatus() == null) return  null;
        return b.equal(r.<StatusEnum>get("status"), criteria.getStatus());
    }

    private Predicate getPeriod(TaskCriteriaDto criteria, CriteriaBuilder b, Root r) {
        if (criteria.getPeriod() == 1) return getPeriodEqualsOne(b , r);
        if (criteria.getPeriod() == 2) return getPeriodEqualsTwo(b , r);
        if (criteria.getPeriod() == 3) return getPeriodEqualsThree(b , r);
        if (criteria.getPeriod() == 4) return getPeriodEqualsFour(b , r);
        return null;
    }

    private Predicate getPeriodEqualsOne(CriteriaBuilder b, Root<Event> r) {
        LocalDateTime now = LocalDateTime.now();
        LocalTime timeNow = now.toLocalTime();
        int hours = timeNow.getHour();
        LocalDateTime before = now.minusHours(hours);
        return b.between(r.get("date"), Date.from(before.atZone(ZoneId.systemDefault()).toInstant()), Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
    }

    private Predicate getPeriodEqualsTwo(CriteriaBuilder b, Root<Event> r) {
        LocalDateTime now = LocalDateTime.now();
        LocalTime timeNow = now.toLocalTime();
        int hours = timeNow.getHour();
        LocalDateTime to = now.minusHours(hours);
        LocalDateTime from = to.minusDays(1);
        return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
    }

    private Predicate getPeriodEqualsThree(CriteriaBuilder b, Root<Event> r) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime to = now;
        LocalDateTime from = to.minusDays(7);
        return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
    }

    private Predicate getPeriodEqualsFour(CriteriaBuilder b, Root<Event> r) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime to = now;
        LocalDateTime from = to.minusMonths(1);
        return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));

    }

    private ResponseDto addUserToRef(User user, User recipient, Event event, TaskDto taskDto) {
        user.getReferences().add(recipient);
        recipient.getReferences().add(user);
        userRepository.save(user);
        userRepository.save(recipient);
        return sendTaskAction(user, recipient, event, taskDto);
    }

    private ResponseDto sendTaskAction(User user, User recipient, Event event, TaskDto taskDto) {
        User patient = userRepository.findOne(taskDto.getPatient().getId());
        if (patient == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        event.setStatus(StatusEnum.SENT);
        event.setFromUser(user);
        event.setToUser(recipient);
        event.setPatient(patient);
        eventRepository.save(event);
        return utils.generateResponse(true, MessageEnums.MSG_TASK_SENT.toString(), null);
    }

    private ResponseDto createTaskForNotPatient(Template template, User patient, User fromUser, JsonNode model) {
        if (utils.isMedicalForm(template)) {
            return checkAlreadyHaveThisTask(template, patient, fromUser, model);
        } else {
            return createTaskLogic(patient, fromUser, template, model);
        }
    }

    private ResponseDto createTaskForPatient(Template template, User patient, User fromUser, JsonNode model) {
        return checkAlreadyHaveThisTask(template, patient, fromUser, model);
    }

    private ResponseDto checkAlreadyHaveThisTask(Template template, User patient, User fromUser, JsonNode model) {
        Long countNew = eventRepository.countByFromUserAndTemplateAndStatus(fromUser, template, StatusEnum.NEW);
        Long countProcessed = eventRepository.countByToUserAndTemplateAndStatus(fromUser, template, StatusEnum.PROCESSED);
        if (countNew > 0 || countProcessed > 0) {
            return utils.generateResponse(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null);
        } else {
            return createTaskLogic(patient, fromUser, template, model);
        }
    }

    private ResponseDto createTaskLogic(User patient, User current, Template template, JsonNode data) {
        if (data == null) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        }
        Event event = new Event();
        event.setFromUser(current);
        event.setPatient(patient);
        event.setDate(new Date());
        event.setTemplate(template);
        event.setData(data.toString());
        event.setStatus(StatusEnum.NEW);
        eventRepository.save(event);
        return utils.generateResponse(true, MessageEnums.MSG_TASK_CREATED.toString(), null);
    }
}
