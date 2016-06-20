package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.PatientDto;
import com.terrasystems.emedics.model.dto.ReferenceCreateRequest;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.TemplateEventDto;

import java.util.List;

public interface EventPatientService {
    List<PatientDto> getAllPatients();
    List<TemplateEventDto> getPatientsEvents(String patientId);
    List <PatientDto> findPatientByCriteria(String search);
    StateDto removePatient(String id);
    StateDto addPatient(String id);


}
