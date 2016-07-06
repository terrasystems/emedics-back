package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.PatientRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.model.mapping.EventMapper;
import com.terrasystems.emedics.model.mapping.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
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
    public List<Patient> getAllPatients(PatientCriteria criteria) {
        User current = userRepository.findByEmail(getPrincipals());
        Sort sort = new Sort(Sort.Direction.ASC, "name");
        List<Patient> patients = patientRepository.findAll(Specifications.<Patient>where((r, q, b) -> {
            Subquery<Patient> sq = q.subquery(Patient.class);
            Root<Doctor> doctor = sq.from(Doctor.class);
            Join<Doctor, Patient> sqPat = doctor.join("patients");
            sq.select(sqPat.get("id")).where(b.equal(doctor.get("id"), current.getId()));
            return b.in(r).value(sq);
        })
        .and((r, q, b) -> {
            if (criteria.getName()==null || criteria.getName().isEmpty()) {
                return  null;
            }
            else{
                return b.like(r.get("name"), "%" + criteria.getName() + "%");
            }
        }), sort);
        return patients;
    }

    @Override
    public List<TemplateEventDto> getPatientsEvents(String patientId) {
        User current = userRepository.findByEmail(getPrincipals());
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
                    List<Event> events = eventRepository.findByPatient_IdAndTemplate_IdAndStatusIsNotAndFromUser_IdOrToUser_Id(patient.getId(),template.getId(),StatusEnum.DECLINED, current.getId(), current.getId());
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
        User current = userRepository.findByEmail(getPrincipals());
        PatientMapper mapper = PatientMapper.getInstance();
        if (current.getDiscriminatorValue().equals("doctor")) {
            List<Patient> currentPats = ((Doctor)current).getPatients();
            List<Patient> patients = patientRepository.findByIdIsNotAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(current.getId(),search,search).stream()
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
        } else {
            List<Patient> currentPats = ((Stuff) current).getDoctor().getPatients();
            List<Patient> patients = patientRepository.findByIdIsNotAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(((Stuff) current).getDoctor().getId(),search,search).stream()
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

    }

    @Override
    @Transactional
    public StateDto removePatient(String id) {
        User current = userRepository.findByEmail(getPrincipals());
        Patient patient = (Patient) userRepository.findOne(id);
        if (current.getDiscriminatorValue().equals("doctor")) {
            ((Doctor)current).getPatients().remove(patient);
            current.getUserRef().remove(patient);
            userRepository.save(current);
            patient.getUserRef().remove(current);
            userRepository.save(patient);
            StateDto state = new StateDto();
            state.setMessage(MessageEnums.MSG_PAT_REMOVE.toString());
            state.setValue(true);
            return state;
        }
        else {
            ((Stuff)current).getDoctor().getPatients().remove(patient);
            ((Stuff) current).getDoctor().getUserRef().remove(patient);
            userRepository.save(((Stuff) current).getDoctor());
            patient.getUserRef().remove(((Stuff) current).getDoctor());
            userRepository.save(patient);
            StateDto state = new StateDto();
            state.setMessage(MessageEnums.MSG_PAT_REMOVE.toString());
            state.setValue(true);
            return state;
        }

    }

    @Override
    @Transactional
    public StateDto addPatient(String id) {
        User current = userRepository.findByEmail(getPrincipals());
        Patient patient = patientRepository.findOne(id);
        StateDto state = new StateDto();
        if (current.getDiscriminatorValue().equals("doctor")) {
            List<Patient> patients = ((Doctor)current).getPatients();
            if (patient == null) {
                state.setValue(false);
                state.setMessage(MessageEnums.MSG_PATS_NOT_EXIST.toString());
                return state;
            } if(patients.contains(patient)) {
                state.setValue(false);
                state.setMessage("This patient exist already");
                return state;
            } else {
                ((Doctor)current).getPatients().add(patient);
                current.getUserRef().add(patient);
                userRepository.save(current);
                patient.getUserRef().add(current);
                userRepository.save(patient);
                state.setValue(true);
                state.setMessage(MessageEnums.MSG_PAT_ADD.toString());
                return state;
            }

        } else {
            List<Patient> patients = ((Stuff)current).getDoctor().getPatients();
            if (patient == null) {
                state.setValue(false);
                state.setMessage(MessageEnums.MSG_PATS_NOT_EXIST.toString());
                return state;
            }
            if (patients.contains(patient)) {
                state.setValue(false);
                state.setMessage("This patient exist already");
                return state;
            } else {
                ((Stuff) current).getDoctor().getPatients().add(patient);
                ((Stuff) current).getDoctor().getUserRef().add(patient);
                userRepository.save(((Stuff) current).getDoctor());
                patient.getUserRef().add(((Stuff) current).getDoctor());
                userRepository.save(patient);
                state.setValue(true);
                state.setMessage(MessageEnums.MSG_PAT_ADD.toString());
                return state;
            }
        }
    }

    /*PatientMapper mapper = PatientMapper.getInstance();
    User current =  userRepository.findByEmail(getPrincipals());
    if (current.getDiscriminatorValue().equals("doctor")) {
        return ((Doctor)current).getPatients().stream()
                .map(patient -> mapper.toDto(patient))
                .collect(Collectors.toList());
    } else if (current.getDiscriminatorValue().equals("stuff")) {
        return ((Stuff) current).getDoctor().getPatients().stream()
                .map(patient -> mapper.toDto(patient))
                .collect(Collectors.toList());
    }
    return null;*/




}
