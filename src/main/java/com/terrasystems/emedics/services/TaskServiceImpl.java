package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.*;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.enums.TypeEnum;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.TaskSearchCriteria;
import com.terrasystems.emedics.model.dto.UserTemplateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService, CurrentUserService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    UserTemplateRepository userTemplateRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    StuffRepository stuffRepository;
    @Autowired
    EventNotificationService eventNotificationService;



    private Event createTaskLogic(User patient, User current, Template template, String data) {
        Event event = new Event();
        event.setFromUser(current);
        event.setPatient(patient);
        event.setDate(new Date());
        event.setTemplate(template);
        event.setData(data);
        event.setStatus(StatusEnum.NEW);
        eventRepository.save(event);
        return event;
    }


    @Override
    public Event createTask(UserTemplateDto userTemplate, String patientId, String fromId, String data) {
        Template template = templateRepository.findOne(userTemplate.getId());
        User patient = null;
        if (patientId != null) {
            patient = userRepository.findOne(patientId);
        }
        User fromUser = userRepository.findOne(fromId);
        if (fromUser.getDiscriminatorValue().equals("doctor")) {
            if (template.getTypeEnum().equals(TypeEnum.MEDICAL)) {
                Long countNew = eventRepository.countByFromUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.NEW);
                Long countProcessed = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.PROCESSED);
                if (countNew > 0 || countProcessed > 0) {
                    return null;
                } else {
                    Event event = createTaskLogic(patient, fromUser, template, data);
                    return event;
                }

            } else {
                Event event = createTaskLogic(patient, fromUser, template, data);
                return event;
            }
        } else if (fromUser.getDiscriminatorValue().equals("patient")) {
            Long countNew = eventRepository.countByFromUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.NEW);
            Long countProcessed = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.PROCESSED);
            if (countNew > 0 || countProcessed > 0) {
                return null;
            }

            Event event = createTaskLogic(patient, fromUser, template, data);
            return event;
        } else if (fromUser.getDiscriminatorValue().equals("stuff")) {
            Long countNew = eventRepository.countByFromUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.NEW);
            Long countProcessed = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.PROCESSED);
            if (countNew > 0 || countProcessed > 0) {
                return null;
            }

            Event event = createTaskLogic(patient, fromUser, template, data);
            return event;
        } else {
            return null;
        }
    }

    @Override
    public List<Event> getAllTasks(TaskSearchCriteria criteria) {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events;
        /*events.addAll(eventRepository.findByFromUser_IdAndStatus(current.getId(),StatusEnum.NEW));
        events.addAll(eventRepository.findByToUser_IdAndStatus(current.getId(),StatusEnum.ACCEPTED));
        */
        events = eventRepository.findAll(Specifications.<Event>where((r, p, b) -> {
            Predicate from = b.equal(r.<User>get("fromUser").get("id"), current.getId());
            Predicate to = b.equal(r.<User>get("toUser").get("id"), current.getId());
            Predicate statusNew = b.equal(r.<StatusEnum>get("status"), StatusEnum.NEW);
            Predicate statusProcessed = b.equal(r.<StatusEnum>get("status"), StatusEnum.PROCESSED);
            Predicate fromNew = b.and(from, statusNew);
            Predicate toProcessed = b.and(from, statusProcessed);
            return b.or(fromNew, toProcessed);
        })
        .and((r, q, b) -> {
            if (criteria.getTemplateName()==null || criteria.getTemplateName().isEmpty()) {
                return  null;
            }
            else{
                return b.like(r.<Template>get("template").<String>get("id"), "%" + criteria.getTemplateName() + "%");
            }

        })
        .and((r, q, b) -> {
                if(criteria.getStatusEnum()==null) {
                    return null;
                } else {
                    return b.equal(r.<StatusEnum>get("status"), criteria.getStatusEnum());
                }
            })
        .and((r, q, b) -> {
                    if (criteria.getPatientName()==null || criteria.getPatientName().isEmpty()) {
                        return  null;
                    }
            else{
                        return b.like(r.<User>get("patient").<String>get("name"), "%" + criteria.getPatientName() + "%");
                    }

                })
        .and((r, q, b) -> {
                    if (criteria.getPeriod() == 1) {
                        LocalDateTime now = LocalDateTime.now();
                        LocalTime timeNow = now.toLocalTime();
                        int hours = timeNow.getHour();
                        LocalDateTime before = now.minusHours(hours);
                        return b.between(r.get("date"), Date.from(before.atZone(ZoneId.systemDefault()).toInstant()), Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
                    }
                    if (criteria.getPeriod() == 2) {
                        LocalDateTime now = LocalDateTime.now();
                        LocalTime timeNow = now.toLocalTime();
                        int hours = timeNow.getHour();
                        LocalDateTime to = now.minusHours(hours);
                        LocalDateTime from = to.minusDays(1);
                        return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
                    }
                    if (criteria.getPeriod() == 3) {
                        LocalDateTime now = LocalDateTime.now();
                        LocalTime timeNow = now.toLocalTime();
                        int hours = timeNow.getHour();
                        LocalDateTime to = now;
                        LocalDateTime from = to.minusDays(7);
                        return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
                    }
                    if (criteria.getPeriod() == 4) {
                        LocalDateTime now = LocalDateTime.now();
                        LocalTime timeNow = now.toLocalTime();
                        int hours = timeNow.getHour();
                        LocalDateTime to = now;
                        LocalDateTime from = to.minusMonths(1);
                        return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));

                    }
                    return null;
                }));


        return events;
    }


    @Override
    public StateDto closeTask(String id) {
        User current = userRepository.findByEmail(getPrincipals());
        StateDto stateDto = new StateDto();
        List<Event> events = new ArrayList<>();
        events.addAll(eventRepository.findByFromUser_IdAndStatus(current.getId(), StatusEnum.NEW));
        List<String> idNewTemplates = new ArrayList<>();
        for(int i = 0; i < events.size(); i++) {
            idNewTemplates.add(events.get(i).getId());
        }
        if(idNewTemplates.contains(id)) {
            Event event = eventRepository.findOne(id);
            event.setStatus(StatusEnum.CLOSED);
            eventRepository.save(event);
            stateDto.setValue(true);
            stateDto.setMessage("Task closed");
            return stateDto;
        } else {
            stateDto.setValue(false);
            stateDto.setMessage("You can close tasks with only NEW status");
            return stateDto;
        }
    }

    @Override
    public List<Event> getByCriteria(TaskSearchCriteria criteria) {
        /*User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = eventRepository.findAll(Specifications.<Event>where((r, p, b) -> {
            Predicate from = b.equal(r.<User>get("fromUser").get("id"), current.getId());
            Predicate to = b.equal(r.<User>get("toUser").get("id"), current.getId());
            Predicate fromto = b.or(from,to);
            Predicate close =  b.equal(r.<StatusEnum>get("status"), StatusEnum.CLOSED);
            return b.and(fromto, close);
        })
        .and((r, q, b) -> {
            if (criteria.getTemplateName() != null) {
                return b.like(r.<Template>get("template").<String>get("name"), "%" + criteria.getTemplateName() + "%");
            }
            return null;
        })
        .and((r, q, b) -> {
            if (criteria.getPatientName() != null) {
                return b.like(r.<User>get("patient").<String>get("name"), "%" + criteria.getPatientName() + "%");
            }
            return null;
        })
        .and((r, q, b) -> {
            if (criteria.getPeriod() == 1) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime before = now.minusDays(2);
                return b.between(r.get("date"), Date.from(before.atZone(ZoneId.systemDefault()).toInstant()), Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
            }
            return null;
        })
        .and());*/
        return null;
    }

    @Override
    @Transactional
    public StateDto multiCreateTask(String templateId, List<String> patients, String message, boolean assignAll) {
        if (patients == null) {
            return new StateDto(false, "U mast choose patient");
        }
        String stateMessage = "";
        User current = userRepository.findByEmail(getPrincipals());

        if(assignAll) {
            if(current.getDiscriminatorValue().equals("doctor")){
                Doctor doctor = doctorRepository.findOne(current.getId());
                List<Patient> patientList = doctor.getPatients();
                List<String> patientsId = new ArrayList<>();
                for(Patient patient : patientList) {
                    UserTemplateDto userTemplateDto = new UserTemplateDto();
                    userTemplateDto.setId(templateId);
                    createTask(userTemplateDto, patient.getId(), current.getId(), "{}");
                }
                return new StateDto(true, "Tasks created");
            } else if(current.getDiscriminatorValue().equals("stuff")) {
                return new StateDto(true, stateMessage);
            }

        }

            if(current.getDiscriminatorValue().equals("doctor")) {
                for (String patientId: patients) {
                    UserTemplateDto userTemplateDto = new UserTemplateDto();
                    userTemplateDto.setId(templateId);
                    //TODO deal with {} hardcode
                    Event event = createTask(userTemplateDto, patientId, current.getId(), "{}");
                    //StateDto stateDto = eventNotificationService.sentAction(event.getId(), patientId, message, patientId);
                    //stateMessage = stateMessage + stateDto.getMessage() + " ";
                }
            }  else if (current.getDiscriminatorValue().equals("stuff")) {
                return new StateDto(true, stateMessage);
            }

        return new StateDto(true, "Tasks created");
    }
    private void createTaskForAllPatients(String doctorId) {

    }

    @Override
    public Event findUserTask(String templateId) {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = eventRepository.findByFromUser_IdAndTemplate_IdAndStatus(current.getId(), templateId, StatusEnum.NEW);
        if(events.isEmpty()) {
            return null;
        }
        Event event = events.get(0);
        return event;
    }

    @Transactional
    public StateDto StuffMultiSend(User current, User recipient, String patientId, String eventId, String message) {
        Stuff stuff = stuffRepository.findOne(current.getId());
        Doctor doctor = stuff.getDoctor();
        Event event = eventRepository.findOne(eventId);
        if(event != null && recipient != null){
            if(!doctor.getUserRef().contains(recipient)) {
                if(recipient.getDiscriminatorValue().equals("patient")) {
                    doctor.getUserRef().add(recipient);
                    doctor.getPatients().add((Patient) recipient);
                    recipient.getUserRef().add(doctor);
                    userRepository.save(doctor);
                    userRepository.save(recipient);
                } else if (recipient.getDiscriminatorValue().equals("doctor")) {
                    doctor.getUserRef().add(recipient);
                    recipient.getUserRef().add(doctor);
                    userRepository.save(doctor);
                    userRepository.save(recipient);
                }
            }

            User patient = userRepository.findOne(patientId);
            event.setStatus(StatusEnum.SENT);
            event.setFromUser(current);
            event.setToUser(recipient);
            event.setDescr(message);
            event.setPatient(patient);
            eventRepository.save(event);
            return new StateDto(true, "Notification send to " + recipient.getName());
        } else {
            return new StateDto(false,"Event with such id or recipient doesn't exist");
        }
    }

    @Override
    public List<Event> getHistory(TaskSearchCriteria criteria) {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = eventRepository.findAll(Specifications.<Event>where((r, p, b) -> {
            Predicate from = b.equal(r.<User>get("fromUser").get("id"), current.getId());
            Predicate to = b.equal(r.<User>get("toUser").get("id"), current.getId());
            Predicate fromto = b.or(from,to);
            Predicate close =  b.equal(r.<StatusEnum>get("status"), StatusEnum.CLOSED);
            return b.and(fromto, close);
        })
        .and((r, q, b) -> {
                    if (criteria.getTemplateName()==null || criteria.getTemplateName().isEmpty()) {
                        return  null;
                    }
                    else{
                        return b.like(r.<Template>get("template").<String>get("id"), criteria.getTemplateName());
                    }
                })
        .and((r, q, b) -> {
                    if (criteria.getPatientName()==null || criteria.getPatientName().isEmpty()) {
                        return  null;
                    }
                    else{
                        return b.like(r.<User>get("patient").<String>get("name"), "%" + criteria.getPatientName() + "%");
                    }
                })
        .and((r, q, b) -> {
                    if (criteria.getPeriod() == 1) {
                        LocalDateTime now = LocalDateTime.now();
                        LocalTime timeNow = now.toLocalTime();
                        int hours = timeNow.getHour();
                        LocalDateTime before = now.minusHours(hours);
                        return b.between(r.get("date"), Date.from(before.atZone(ZoneId.systemDefault()).toInstant()), Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
                    }
                    if (criteria.getPeriod() == 2) {
                        LocalDateTime now = LocalDateTime.now();
                        LocalTime timeNow = now.toLocalTime();
                        int hours = timeNow.getHour();
                        LocalDateTime to = now.minusHours(hours);
                        LocalDateTime from = to.minusDays(1);
                        return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
                    }
                    if (criteria.getPeriod() == 3) {
                        LocalDateTime now = LocalDateTime.now();
                        LocalTime timeNow = now.toLocalTime();
                        int hours = timeNow.getHour();
                        LocalDateTime to = now;
                        LocalDateTime from = to.minusDays(7);
                        return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));
                    }
                    if (criteria.getPeriod() == 4) {
                        LocalDateTime now = LocalDateTime.now();
                        LocalTime timeNow = now.toLocalTime();
                        int hours = timeNow.getHour();
                        LocalDateTime to = now;
                        LocalDateTime from = to.minusMonths(1);
                        return b.between(r.get("date"), Date.from(from.atZone(ZoneId.systemDefault()).toInstant()), Date.from(to.atZone(ZoneId.systemDefault()).toInstant()));

                    }
                    return null;
                }));

        return events;
    }

    @Override
    public Event getTask(String id) {
        return eventRepository.findOne(id);
    }

    @Override
    public Event editTask(EventDto eventDto) {
        Event event = eventRepository.findOne(eventDto.getId());
        event.setDate(new Date());
        event.setData(eventDto.getData().toString());
        event.setStatus(StatusEnum.PROCESSED);
        return eventRepository.save(event);
    }
}


