package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.*;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.model.mapping.EventMapper;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    PatientRepository patientRepository;


    @Override
    public List<Stuff> getAllStuff() {
        User current = userRepository.findByEmail(getPrincipals());
        if (current.getDiscriminatorValue().equals("doctor")) {
            return ((Doctor)current).getStuff();
        } else return  ((Stuff) current).getDoctor().getStuff();
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
        if(!stuff.getPassword().equals(dto.getPassword())){
            mailService.sendMailToStuffIfAdminChangedPassword(stuff.getEmail(), dto.getPassword());
        }
        stuff.setPassword(dto.getPassword());
        stuff.setEmail(dto.getEmail());
        stuff.setEmail(dto.getEmail());
        stuff.setFirstName(dto.getFirstName());
        stuff.setLastName(dto.getLastName());
        stuff.setBirth(dto.getBirth());
        stuff.setTypeExp(dto.getTypeExp());


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

    @Override
    public List<ReferenceDto> getAllReferences() {
        Stuff stuff = (Stuff) userRepository.findByEmail(getPrincipals());
        Doctor doctor = stuff.getDoctor();
        ReferenceConverter converter = new ReferenceConverter();

        return converter.convertFromUsers(doctor.getUserRef());
    }

    @Override
    @Transactional
    public StateDto addReferences(String reference) {
        Stuff stuff = (Stuff) userRepository.findByEmail(getPrincipals());
        Doctor doctor = stuff.getDoctor();
        User refToAdd = userRepository.findOne(reference);
        if (refToAdd.getDiscriminatorValue().equals("patient")) {
            List<Patient> patients = doctor.getPatients();
            if(!patients.contains(refToAdd)) {
                doctor.getUserRef().add(refToAdd);
                refToAdd.getUserRef().add(doctor);
                doctor.getPatients().add((Patient) refToAdd);
                ((Patient) refToAdd).getDoctors().add(doctor);
                doctorRepository.save(doctor);
                userRepository.save(refToAdd);
                return new StateDto(true, "Reference saved");
            }
            return new StateDto(true, "Reference saved");
        } else {
            doctor.getUserRef().add(refToAdd);
            refToAdd.getUserRef().add(doctor);
            doctorRepository.save(doctor);
            userRepository.save(refToAdd);
            return new StateDto(true, "Reference saved");
        }

    }

    @Override
    public List<ReferenceDto> findOrgReferencesByCriteria(String search) {
        ReferenceConverter converter = new ReferenceConverter();
        Stuff stuff = (Stuff) userRepository.findByEmail(getPrincipals());
        Doctor doctor = stuff.getDoctor();
        String docId = doctor.getId();
        Set<User> currentRefs = doctor.getUserRef();
        List<ReferenceDto> refs = new ArrayList<>();
        List<Doctor> doctorsRefs = doctorRepository.findByIdIsNotAndNameContainingIgnoreCaseOrIdIsNotAndEmailContainingIgnoreCaseOrIdIsNotAndType_NameContainingIgnoreCase(docId, search, docId, search, docId, search);
        List<Patient> patientsRefs = patientRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(search,search).stream()
                .filter(patient -> !currentRefs.contains(patient))
                .collect(Collectors.toList());
        refs.addAll(converter.convertFromDoctors(doctorsRefs));
        refs.addAll(converter.convertFromPatients(patientsRefs));
        return refs;
    }

    @Override
    public StateDto inactiveStuff(String id) {
        Stuff stuff = stuffRepository.findOne(id);
        if(stuff == null) {
            return new StateDto(false, "User with such id doesn't exist");
        }
        stuff.setEnabled(false);
        stuffRepository.save(stuff);
        return new StateDto(true, "User disabled");
    }

    @Override
    @Transactional
    public Event assignTask(String stuffId, String eventId) {
        Stuff stuff = stuffRepository.findOne(stuffId);
        Event event = eventRepository.findOne(eventId);
        event.setFromUser(stuff);
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public ObjectResponse editTask(EventDto eventDto) {
        EventMapper mapper = EventMapper.getInstance();
        ObjectResponse response = new ObjectResponse();
        User current = userRepository.findByEmail(getPrincipals());
        if(current.getDiscriminatorValue().equals("doctor")){
            Doctor doctor = doctorRepository.findOne(current.getId());
            List<Stuff> stuffs = doctor.getStuff();
            Stuff stuff = (Stuff) userRepository.findOne(eventDto.getFromUser().getId());
            if(current.getOrg()&&stuffs.contains(stuff)){
                Event event = eventRepository.findOne(eventDto.getId());
                event.setDate(new Date());
                event.setData(eventDto.getData().toString());
                try {
                    response.setResult(mapper.toDto(eventRepository.save(event)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.setState(new StateDto(true, "Task Edited"));
                return response;
            } else {
                response.setState(new StateDto(false, "You aren't Admin or this task don't belong your stuff"));
                return response;
            }
        } else {
            response.setState(new StateDto(false, "You aren't Admin"));
            return response;
        }
    }

    @Override
    public StateDto closeTask(String id) {
        User current = userRepository.findByEmail(getPrincipals());
        Event event = eventRepository.findOne(id);
        if(current.getDiscriminatorValue().equals("doctor")){
            Doctor doctor = doctorRepository.findOne(current.getId());
            List<Stuff> stuffs = doctor.getStuff();
            Stuff stuff = (Stuff) userRepository.findOne(event.getFromUser().getId());
            if(event.getStatus().equals(StatusEnum.NEW)){
                if(current.getOrg()&&stuffs.contains(stuff)){
                    event.setStatus(StatusEnum.CLOSED);
                    eventRepository.save(event);
                    return new StateDto(true, "Task closed");
                } else {
                    return new StateDto(false, "You aren't Admin or this task don't belong your stuff");
                }
            } else {
                return new StateDto(false, "You can close tasks with only NEW status");
            }
        } else {
            return new StateDto(false, "You aren't Admin");
        }
    }


}
