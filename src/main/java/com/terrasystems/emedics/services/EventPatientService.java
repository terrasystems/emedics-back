package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dto.PatientDto;
import com.terrasystems.emedics.model.dto.TemplateEventDto;

import java.util.List;

public interface EventPatientService {
    List<PatientDto> getAllPatients();
    List<TemplateEventDto> getPatientsEvents();

}