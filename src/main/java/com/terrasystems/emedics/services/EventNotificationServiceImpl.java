package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.EventMapper;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventNotificationServiceImpl implements EventNotificationService, CurrentUserService {

    @Autowired
    EventRepository  eventRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public StateDto sentAction(String eventId, String toUser, String message, String patientId) {
        if (patientId == null) {
            return new StateDto(false, "U mas choose patient");
        }
        User current = userRepository.findByEmail(getPrincipals());
        Event event = eventRepository.findOne(eventId);
        User recipient = userRepository.findOne(toUser);
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
    public StateDto acceptNotification(String eventId) {
        Event event = eventRepository.findOne(eventId);

        if (event != null) {
            Event newEvent = cloneEvent(event);
            newEvent.setId(null);
            event.setStatus(StatusEnum.CLOSED);
            eventRepository.save(event);
            eventRepository.save(newEvent);
            return new StateDto(true, "Notification accepted");
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