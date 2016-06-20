package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.PatientRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.model.mapping.EventMapper;
import com.terrasystems.emedics.model.mapping.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventPatientServiceImpl implements EventPatientService, CurrentUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TemplateRepository templateRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ReferenceCreateService referenceCreateService;
    @Override
    public List<PatientDto> getAllPatients() {
        PatientMapper mapper = PatientMapper.getInstance();
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());

        return current.getPatients().stream()
                .map(patient -> mapper.toDto(patient))
                .collect(Collectors.toList());
    }

    @Override
    public List<TemplateEventDto> getPatientsEvents(String patientId) {
        Patient patient = (Patient) userRepository.findOne(patientId);
        if (patient == null) {
            return null;
        }
        List<String> templates = eventRepository.findTemplate_IdByPatient_Id(patient.getId());
        EventMapper mapper = EventMapper.getInstance();
        List<TemplateEventDto> eventsDto = templates.stream()
                .map(s -> {
                    TemplateEventDto dto = new TemplateEventDto();
                    Template template = templateRepository.findOne(s);
                    List<Event> events = eventRepository.findByPatient_IdAndTemplate_IdAndStatusIsNot(patient.getId(),template.getId(),StatusEnum.DECLINED);
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
    public List<PatientDto> findPatientByCriteria(String search) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        List<Patient> currentPats = current.getPatients();
        PatientMapper mapper = PatientMapper.getInstance();
        List<Patient> patients = patientRepository.findByIdIsNotAndNameContainingOrEmailContaining(current.getId(),search,search).stream()
                .filter(patient -> !currentPats.contains(patient))
                .collect(Collectors.toList());
        List<PatientDto> patientDtos = patients.stream()
                .map(patient -> {
                    PatientDto dto = new PatientDto();
                    dto = mapper.toDto(patient);
                    return dto;
                })
                .collect(Collectors.toList());


        return patientDtos;
    }

    @Override
    @Transactional
    public StateDto removePatient(String id) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        Patient patient = (Patient) userRepository.findOne(id);
        current.getPatients().remove(patient);
        current.getUserRef().remove(patient);
        userRepository.save(current);
        patient.getUserRef().remove(current);
        userRepository.save(patient);
        StateDto state = new StateDto();
        state.setMessage(MessageEnums.MSG_PAT_REMOVE.toString());
        state.setValue(true);
        return state;
    }

    @Override
    @Transactional
    public StateDto addPatient(String id) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        Patient patient = patientRepository.findOne(id);
        StateDto state = new StateDto();
        List<Patient> patients = current.getPatients();
        if (patient == null) {
            state.setValue(false);
            state.setMessage(MessageEnums.MSG_PATS_NOT_EXIST.toString());
            return state;
        } if(patients.contains(patient)) {
            state.setValue(false);
            state.setMessage("This patient exist already");
            return state;
        } else {
            current.getPatients().add(patient);
            current.getUserRef().add(patient);
            userRepository.save(current);
            patient.getUserRef().add(current);
            userRepository.save(patient);
            state.setValue(true);
            state.setMessage(MessageEnums.MSG_PAT_ADD.toString());
            return state;
        }
    }




}
