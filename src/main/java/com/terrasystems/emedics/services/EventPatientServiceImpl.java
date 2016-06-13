package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.dao.TemplateRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.PatientDto;
import com.terrasystems.emedics.model.dto.TemplateEventDto;
import com.terrasystems.emedics.model.mapping.EventMapper;
import com.terrasystems.emedics.model.mapping.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .collect(Collectors.toList());

        return eventsDto;
    }

    private List<Event> patientEventsByTemplate (String patientId, String templateId) {

        return eventRepository.findByPatient_IdAndTemplate_IdAndStatusIsNot(patientId, templateId, StatusEnum.DECLINED);
    }
}
