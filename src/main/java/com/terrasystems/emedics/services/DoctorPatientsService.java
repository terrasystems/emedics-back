package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dto.HistoryDto;
import com.terrasystems.emedics.model.dto.PatientDto;
import com.terrasystems.emedics.model.dto.StateDto;

import java.util.List;

public interface DoctorPatientsService {
    StateDto patientAdd(String id);
    PatientDto getPatientById(String id);
    List<PatientDto> allPatients();
    List<PatientDto> findPatientsByCriteria(String search);
    HistoryDto getPatientHistory(String id);
    StateDto editPatientHistory(HistoryDto dto);

}
