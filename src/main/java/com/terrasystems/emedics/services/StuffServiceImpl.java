package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StuffDto;
import com.terrasystems.emedics.model.dto.TemplateEventDto;
import com.terrasystems.emedics.model.mapping.EventMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToOne;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StuffServiceImpl implements StuffService, CurrentUserService {

    @Autowired
    StuffRepository stuffRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    TemplateRepository templateRepository;


    @Override
    public List<Stuff> getAllStuff() {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        return current.getStuff();
    }

    @Override
    public Stuff getById(String id) {
        return stuffRepository.findOne(id);
    }

    @Override
    @Transactional
    public Stuff createStuff(StuffDto dto) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        if (userRepository.existsByEmail(dto.getEmail())) {
            return null;
        }
        Stuff stuff = new Stuff();
        stuff.setUsers(new HashSet<>());
        stuff.setUserRef(new HashSet<>());
        Set<Role> roles = new HashSet<>();
        Role role1 = new Role("ROLE_STUFF");
        role1.setUser(stuff);
        roles.add(role1);

        stuff.setRoles(roles);
        stuff.setDoctor(current);
        if (dto.getPassword() == null) {
            stuff.setPassword(RandomStringUtils.random(10, 'a','b','c','A','B','C','1','2','3','4','5'));
        } else {
            stuff.setPassword(dto.getPassword());
        }
        stuff.setFirstName(dto.getFirstName());
        stuff.setLastName(dto.getLastName());
        stuff.setEmail(dto.getEmail());
        stuff.setPhone(dto.getPhone());
        stuff.setBirth(dto.getBirth());
        stuff.setEnabled(true);
        stuff.setRegistrationDate(new Date());
        if (stuffRepository.save(stuff) == null) {
            return null;
        }

        if (!mailService.sendStuffMail(stuff.getEmail(), stuff.getPassword()).isValue()) {
            return null;
        }
        return stuff;
    }

    @Override
    public void deleteStuff(String id) {
        stuffRepository.delete(id);
    }

    @Override
    @Transactional
    public Stuff updateStuff(StuffDto dto) {
        Stuff stuff = stuffRepository.findOne(dto.getId());
        stuff.setPhone(dto.getPhone());
        stuff.setEmail(dto.getEmail());
        stuff.setFirstName(dto.getFirstName());
        stuff.setLastName(dto.getLastName());
        stuff.setBirth(dto.getBirth());
        stuff.setPassword(dto.getPassword());


        return stuffRepository.save(stuff);
    }

    @Override
    public List<TemplateEventDto> getStuffEvents(String stuffId) {
        Stuff stuff = (Stuff) userRepository.findOne(stuffId);
        if (stuff == null) {
            return null;
        }
        List<String> templates = eventRepository.findTemplate_IdByStuff_Id(stuff.getId());
        EventMapper mapper = EventMapper.getInstance();
        List<TemplateEventDto> eventsDto = templates.stream()
                .map(s -> {
                    TemplateEventDto dto = new TemplateEventDto();
                    Template template = templateRepository.findOne(s);
                    List<Event> events = eventRepository.findByFromUser_IdAndTemplate_IdAndStatus(stuff.getId(),template.getId(), StatusEnum.NEW);
                    dto.setName(template.getName());
                    dto.setId(template.getId());
                    List<EventDto> dtos = new ArrayList<>();
                    for (Event event : events) {
                        try {
                            dtos.add(mapper.toDto(event));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    dto.setEvents(dtos);
                    return dto;
                })
                .collect(Collectors.toList())
                .stream()
                .filter(templateEventDto -> !templateEventDto.getEvents().isEmpty())
                .collect(Collectors.toList());

        return eventsDto;
    }
}
