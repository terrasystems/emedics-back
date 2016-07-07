package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.PatientRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.enums.TypeEnum;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.NotificationCriteria;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventNotificationServiceImpl implements EventNotificationService, CurrentUserService {

    @Autowired
    EventRepository  eventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskService taskService;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;

    @Override
    @Transactional
    public StateDto sentAction(String eventId, String toUser, String message, String patientId) {
        if (patientId == null) {
            return new StateDto(false, "U mast choose patient");
        }
        User current = userRepository.findByEmail(getPrincipals());
        Event event = eventRepository.findOne(eventId);
        User recipient = userRepository.findOne(toUser);
        if(event != null && recipient != null){
            if (recipient.getDiscriminatorValue().equals("patient") && event.getTemplate().getTypeEnum().equals(TypeEnum.MEDICAL)) {
                return new StateDto(false, "U can't send this form to patients");
            }
            if(!current.getUserRef().contains(recipient)) {
                if(current.getDiscriminatorValue().equals("doctor")&&recipient.getDiscriminatorValue().equals("patient")){
                    Doctor currentDoctor = doctorRepository.findOne(current.getId());
                    Patient currentPatient = patientRepository.findOne(recipient.getId());
                    currentDoctor.getUserRef().add(currentPatient);
                    currentDoctor.getPatients().add(currentPatient);
                    currentPatient.getUserRef().add(currentDoctor);
                    userRepository.save(currentDoctor);
                    userRepository.save(currentPatient);
                } else if (current.getDiscriminatorValue().equals("doctor")&&recipient.getDiscriminatorValue().equals("doctor")) {
                    Doctor currentDoctor = doctorRepository.findOne(current.getId());
                    Doctor toDoctor = doctorRepository.findOne(recipient.getId());
                    current.getUserRef().add(recipient);
                    recipient.getUserRef().add(current);
                    userRepository.save(currentDoctor);
                    userRepository.save(toDoctor);
                } else if (current.getDiscriminatorValue().equals("patient")&&recipient.getDiscriminatorValue().equals("doctor")) {
                    Doctor doctor = doctorRepository.findOne(recipient.getId());
                    Patient patient = patientRepository.findOne(current.getId());
                    doctor.getPatients().add(patient);
                    doctor.getUserRef().add(patient);
                    patient.getUserRef().add(doctor);
                    userRepository.save(doctor);
                    userRepository.save(patient);
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
    public StateDto declineNotification(String eventId) {
        Event event = eventRepository.findOne(eventId);
        if(event != null) {
            event.setStatus(StatusEnum.DECLINED);
            eventRepository.save(event);
            return new StateDto(true, "Notification declined");
        } else {
            return new StateDto(false, "Event with such id or recipient doesn't exist");
        }
    }

    @Override
    @Transactional
    public StateDto acceptNotification(String eventId) {
        Event event = eventRepository.findOne(eventId);
        User current = userRepository.findByEmail(getPrincipals());
        if(event != null){
            if(current.getDiscriminatorValue().equals("doctor")){
                if(event.getTemplate().getTypeEnum().equals(TypeEnum.MEDICAL)){
                    Long countNew = eventRepository.countByFromUser_IdAndTemplate_IdAndStatus(current.getId(),event.getTemplate().getId(),StatusEnum.NEW);
                    Long countProcessed = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(current.getId(),event.getTemplate().getId(),StatusEnum.PROCESSED);
                    if (countNew > 0 || countProcessed > 0) {
                        return new StateDto(false, "You already have this task");
                    } else {
                        Event newEvent = cloneEvent(event);
                        newEvent.setId(null);
                        event.setStatus(StatusEnum.CLOSED);
                        eventRepository.save(event);
                        eventRepository.save(newEvent);
                        return new StateDto(true, "Notification accepted");
                    }
                } else {
                    Event newEvent = cloneEvent(event);
                    newEvent.setId(null);
                    event.setStatus(StatusEnum.CLOSED);
                    eventRepository.save(event);
                    eventRepository.save(newEvent);
                    return new StateDto(true, "Notification accepted");
                }
            } else if (current.getDiscriminatorValue().equals("patient")) {
                Long countNew = eventRepository.countByFromUser_IdAndTemplate_IdAndStatus(current.getId(),event.getTemplate().getId(),StatusEnum.NEW);
                Long countProcessed = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(current.getId(),event.getTemplate().getId(),StatusEnum.PROCESSED);
                if (countNew > 0 || countProcessed > 0) {
                    return new StateDto(false, "You already have this task");
                } else {
                    Event newEvent = cloneEvent(event);
                    newEvent.setId(null);
                    event.setStatus(StatusEnum.CLOSED);
                    eventRepository.save(event);
                    eventRepository.save(newEvent);
                    return new StateDto(true, "Notification accepted");
                }
            } else {
                return null;
            }
        } else {
            return new StateDto(false, "Event with such id or recipient doesn't exist");
        }

    }

    @Override
    @Transactional
    public List<EventDto> getNotifications() {
        User current = userRepository.findByEmail(getPrincipals());
        List<EventDto> eventDtos = eventRepository.findByToUser_IdAndStatus(current.getId(),StatusEnum.SENT).stream()
                .map(item -> {
                    EventMapper mapper = EventMapper.getInstance();
                    EventDto eventDto = new EventDto();
                    try {
                        eventDto = mapper.toDto(item);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return  eventDto;
                }).collect(Collectors.toList());

        return eventDtos;
    }
    @Transactional
    public List<EventDto> getNotifications(NotificationCriteria criteria) {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = eventRepository.findAll(Specifications.<Event>where((r, q, b) -> {
            Predicate sentTo = b.equal(r.<User>get("toUser").<String>get("id"), current.getId());
            Predicate status = b.equal(r.get("status"), StatusEnum.SENT);
            return b.and(sentTo,status);
        })
        .and((r, q, b) -> {
            if (criteria.getDescription()==null || criteria.getDescription().isEmpty()) {
                return  null;
            }
            else{
                return b.like(r.get("descr"), "%" + criteria.getDescription() + "%");
            }
        })
        .and((r, q, b) -> {
            if (criteria.getFormType() == null) {
                return null;
            } else {
                return b.equal(r.<Template>get("template").get("typeEnum"), criteria.getFormType());
            }
        })
        .and((r, q, b) -> {
            if (criteria.getTemplateId() == null || criteria.getTemplateId().isEmpty()) {
                return null;
            } else {
                return b.equal(r.<Template>get("template").get("id"), criteria.getTemplateId());
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
        return null;
    }

    private static Event cloneEvent(Event event) {
        Event newEvent = new Event();
        newEvent.setStatus(StatusEnum.PROCESSED);
        newEvent.setData(event.getData());
        newEvent.setFromUser(event.getToUser());
        newEvent.setDescr(event.getDescr());
        newEvent.setDate(new Date());
        newEvent.setPatient(event.getPatient());
        newEvent.setTemplate(event.getTemplate());
        newEvent.setToUser(null);
        return newEvent;

    }


}
