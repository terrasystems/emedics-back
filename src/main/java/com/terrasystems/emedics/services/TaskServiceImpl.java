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


    @Override
    public Event createTask(UserTemplateDto userTemplate, String patientId) {
        User current = userRepository.findByEmail(getPrincipals());
        Template template = userTemplateRepository.findOne(userTemplate.getId()).getTemplate();
        Long countNew = eventRepository.countByFromUser_IdAndTemplate_IdAndStatus(current.getId(),template.getId(),StatusEnum.NEW);
        Long countAccepted = eventRepository.countByToUser_IdAndTemplate_IdAndStatus(current.getId(),template.getId(),StatusEnum.ACCEPTED);
        if (countNew > 0 || countAccepted > 0) {
            return null;
        }

        User patient = userRepository.findOne(patientId);
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
    public List<Event> getAllTasks() {
        User current = userRepository.findByEmail(getPrincipals());
        List<Event> events = new ArrayList<>();
        events.addAll(eventRepository.findByFromUser_IdAndStatus(current.getId(),StatusEnum.NEW));
        events.addAll(eventRepository.findByToUser_IdAndStatus(current.getId(),StatusEnum.ACCEPTED));


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
        //event.setStatus(StatusEnum.valueOf(eventDto.getStatus()));

        return eventRepository.save(event);
    }
}
