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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private Event createTaskLogic(User patient, User current, Template template) {
        Event event = new Event();
        event.setFromUser(current);
        event.setPatient(patient);
        event.setDate(new Date());
        event.setTemplate(template);
        event.setData("{}");
        event.setStatus(StatusEnum.NEW);
        eventRepository.save(event);
        return event;
    }


    @Override
    public Event createTask(UserTemplateDto userTemplate, String patientId, String fromId) {
        Template template = templateRepository.findOne(userTemplate.getId());
        User patient = null;
        if (patientId != null) {
            patient = userRepository.findOne(patientId);
        }
        User fromUser = userRepository.findOne(fromId);
        if (fromUser.getDiscriminatorValue().equals("doctor")) {
            if (template.getTypeEnum().equals(TypeEnum.MEDICAL)) {
                Long countNew = eventRepository.countByFromUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.NEW);
                Long countAccepted = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.ACCEPTED);
                if (countNew > 0 || countAccepted > 0) {
                    return null;
                } else {
                    Event event = createTaskLogic(patient, fromUser, template);
                    return event;
                }

            } else {
                Event event = createTaskLogic(patient, fromUser, template);
                return event;
            }
        } else if (fromUser.getDiscriminatorValue().equals("patient")) {
            Long countNew = eventRepository.countByFromUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.NEW);
            Long countAccepted = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.ACCEPTED);
            if (countNew > 0 || countAccepted > 0) {
                return null;
            }

            Event event = createTaskLogic(patient, fromUser, template);
            return event;
        } else if (fromUser.getDiscriminatorValue().equals("stuff")) {
            Long countNew = eventRepository.countByFromUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.NEW);
            Long countAccepted = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(fromUser.getId(), template.getId(), StatusEnum.ACCEPTED);
            if (countNew > 0 || countAccepted > 0) {
                return null;
            }

            Event event = createTaskLogic(patient, fromUser, template);
            return event;
        } else {
            return null;
        }
    }

    @Override
    public List<Event> getAllTasks() {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = new ArrayList<>();
        events.addAll(eventRepository.findByFromUser_IdAndStatus(current.getId(),StatusEnum.NEW));
        events.addAll(eventRepository.findByToUser_IdAndStatus(current.getId(),StatusEnum.ACCEPTED));


        return events;
    }

    /*@Override
    public List<Event> getTasksByTemplate(String id) {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = new ArrayList<>();
        events.addAll(eventRepository.findByTemplate_IdAndStatusAndFromUser_Id(id, StatusEnum.NEW, current.getId()));
        events.addAll(eventRepository.findByTemplate_IdAndStatusAndToUser_Id(id, StatusEnum.ACCEPTED, current.getId()));

        return events;
    }

    @Override
    public List<Event> getTasksByFromUserId(String id) {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = new ArrayList<>();
        events.addAll(eventRepository.findByFromUser_IdAndToUser_IdAndStatus(id, current.getId(), StatusEnum.ACCEPTED));

        return events;
    }

    @Override
    public List<Event> getTasksByPatient(String id) {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = new ArrayList<>();
        events.addAll(eventRepository.findByPatient_IdAndToUser_IdAndStatus(id, current.getId(), StatusEnum.ACCEPTED));

        return events;
    }*/

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
        List<Event> events = new ArrayList<>();
        Date date = new Date();
        Date date1 = new Date();
        switch (criteria.getPeriod()) {
            case 1:
                date1.setHours(0);
                date1.setMinutes(0);
                date1.setSeconds(0);
                break;
            case 2:
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                date1.setDate(date1.getDate() - 1);
                date1.setHours(0);
                date1.setMinutes(0);
                date1.setSeconds(0);
                break;
            case 3:
                date1.setDate(date1.getDate() - 7);
                break;
            case 4:
                date1.setMonth(date1.getMonth() - 1);
                break;
        }
        events.addAll(eventRepository.findByTemplate_IdAndFromUser_IdAndPatient_IdAndDateBetween(criteria.getTemplateId(), criteria.getFromId(), criteria.getPatientId(), date1, date));
        return events;
    }

    @Override
    @Transactional
    public StateDto multiSendTask(String eventId, List<String> toUsers, String message, String patientId) {
        if (patientId == null) {
            return new StateDto(false, "U mast choose patient");
        }
        String stateMessage = "";
        User current = userRepository.findByEmail(getPrincipals());
        Event event = eventRepository.findOne(eventId);
        for (String toUser: toUsers) {
            User recipient = userRepository.findOne(toUser);
            if(current.getDiscriminatorValue().equals("doctor")){
                StateDto stateDto =  eventNotificationService.sentAction(eventId, toUser, message, patientId);
                stateMessage = stateMessage + stateDto.getMessage() + " \n";
            }  else if (current.getDiscriminatorValue().equals("stuff")) {
                StateDto stateDto = StuffMultiSend(current, recipient, patientId, eventId, message);
                stateMessage = stateMessage + stateDto.getMessage() + " \n";
            }
        }
        return new StateDto(true, stateMessage);
    }

    @Transactional
    StateDto StuffMultiSend(User current, User recipient, String patientId, String eventId, String message) {
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
    public List<Event> getHistory() {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = new ArrayList<>();
        events.addAll(eventRepository.findByStatusAndFromUser_IdOrToUser_Id(StatusEnum.CLOSED, current.getId(), current.getId()));
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
        return eventRepository.save(event);
    }
}
