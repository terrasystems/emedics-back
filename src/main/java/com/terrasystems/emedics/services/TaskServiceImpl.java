package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.UserTemplateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService, CurrentUserService {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public Event createTask(UserTemplateDto userTemplate) {
        User current = userRepository.findByEmail(getPrincipals());
        Event event = new Event();
        event.setDate(new Date());
        event.setPatient(current);
        event.setData("{}");
        event.setStatus(StatusEnum.NEW);
        eventRepository.save(event);
        return event;
    }

    @Override
    public List<Event> getAllTasks() {
        return null;
    }

    @Override
    public Event getTask(String id) {
        return eventRepository.findOne(id);
    }

    @Override
    public Event editTask() {
        return null;
    }
}
