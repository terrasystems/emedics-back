package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.dao.UserTemplateRepository;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.UserTemplate;
import com.terrasystems.emedics.model.dto.EventDto;
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
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    UserTemplateRepository userTemplateRepository;


    @Override
    public Event createTask(UserTemplateDto userTemplate) {
        User current = userRepository.findByEmail(getPrincipals());
        Template template = userTemplateRepository.findOne(userTemplate.getId()).getTemplate();
        Event event = new Event();
        event.setDate(new Date());
        event.setPatient(current);
        event.setTemplate(template);
        event.setData("{}");
        event.setStatus(StatusEnum.SENT);
        eventRepository.save(event);
        return event;
    }

    @Override
    public List<Event> getAllTasks() {

        return (List<Event>) eventRepository.findAll();
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
        //event.setStatus(StatusEnum.valueOf(eventDto.getStatus()));

        return eventRepository.save(event);
    }
}
