package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.*;

import java.util.List;

public interface EventPatientService {
    List<Patient> getAllPatients(PatientCriteria criteria);
    List<TemplateEventDto> getPatientsEvents(String patientId);
    List <PatientDto> findPatientByCriteria(String search);
    StateDto removePatient(String id);
    StateDto addPatient(String id);


}
