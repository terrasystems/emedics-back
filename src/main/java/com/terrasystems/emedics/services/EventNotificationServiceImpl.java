package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.PatientRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.enums.TypeEnum;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.EventMapper;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
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
        /*if (current.getDiscriminatorValue().equals("doctor")) {

        }*/
        Event event = eventRepository.findOne(eventId);
        User recipient = userRepository.findOne(toUser);
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
        if(event != null && recipient != null){
            event.setStatus(StatusEnum.SENT);
            event.setFromUser(current);
            event.setToUser(recipient);
            event.setDescr(message);
            event.setPatient(patient);
            eventRepository.save(event);
            return new StateDto(true, "Notification Send");
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
                    Long countAccepted = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(current.getId(),event.getTemplate().getId(),StatusEnum.ACCEPTED);
                    if (countNew > 0 || countAccepted > 0) {
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
                Long countAccepted = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(current.getId(),event.getTemplate().getId(),StatusEnum.ACCEPTED);
                if (countNew > 0 || countAccepted > 0) {
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

    private static Event cloneEvent(Event event) {
        Event newEvent = new Event();
        newEvent.setStatus(StatusEnum.NEW);
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
