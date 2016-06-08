package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    public StateDto sentAction(String eventId, String toUser, String patientId) {
        User current = userRepository.findByEmail(getPrincipals());
        Event event = eventRepository.findOne(eventId);
        User recipient = userRepository.findOne(toUser);
        if(event != null && recipient != null){
            event.setStatus(StatusEnum.SENT);
            event.setFromUser(current);
            event.setToUser(recipient);
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
            event.setStatus(StatusEnum.DECLINDED);
            eventRepository.save(event);
            return new StateDto(true, "Notification declined");
        } else {
            return new StateDto(false, "Event with such id or recipient doesn't exist");
        }
    }

    @Override
    public StateDto acceptNotification(String eventId) {
        Event event = eventRepository.findOne(eventId);
        if(event != null) {
            event.setStatus(StatusEnum.ACCEPTED);
            eventRepository.save(event);
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


}
