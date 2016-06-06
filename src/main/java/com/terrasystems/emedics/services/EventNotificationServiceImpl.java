package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.StateDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EventNotificationServiceImpl implements EventNotificationService, CurrentUserService {

    @Autowired
    EventRepository  eventRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public StateDto sentAction(Event event) {
        User current = userRepository.findByEmail(getPrincipals());
        StateDto state = new StateDto();
        List<Event> events = eventRepository.findByStatusAndTo_user_id("sent", current.getId());


        return null;
    }
}
