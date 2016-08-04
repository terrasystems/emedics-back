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
import com.terrasystems.emedics.model.dtoV2.TaskDto;
import com.terrasystems.emedics.model.dtoV2.UserDto;
import com.terrasystems.emedics.model.mapping.TaskMapper;
import com.terrasystems.emedics.model.mapping.UserMapper;
import com.terrasystems.emedics.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
        Event event = eventRepository.findOne(taskDto.getTemplateDto().getMyTemplateId());
        User recipient = userRepository.findOne(taskDto.getToUser().getId());
        if (event == null || recipient == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        if (utils.isPatient(recipient) && utils.isMedicalForm(event.getTemplate())) return utils.generateResponse(false, MessageEnums.MSG_YOU_CANT_SEND_MED_FORM_TO_PAT.toString(), null);
        if (!user.getReferences().contains(recipient)) return addUserToRef(user, recipient, event, taskDto);
        return sendTaskAction(user, recipient, event, taskDto);
    }

    private ResponseDto addUserToRef(User user, User recipient, Event event, TaskDto taskDto) {
        user.getReferences().add(recipient);
        recipient.getReferences().add(user);
        userRepository.save(user);
        userRepository.save(recipient);
        return sendTaskAction(user, recipient, event, taskDto);
    }

    private ResponseDto sendTaskAction(User user, User recipient, Event event, TaskDto taskDto) {
        User patient =userRepository.findOne(taskDto.getPatient().getId());
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
