package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.PatientRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.PatientDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class DoctoPatientsServiceImpl implements DoctorPatientsService, CurrentUserService {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public StateDto patientAdd(String id) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        Patient patient = patientRepository.findOne(id);
        StateDto state = new StateDto();
        if (patient == null) {
            state.setValue(false);
            state.setMessage("Patient with such id doesn't exist");
            return state;
        }
        current.getPatients().add(patient);
        userRepository.save(current);
        state.setValue(true);
        state.setMessage("Patient added");
        return state;
    }

    @Override
    public PatientDto getPatientById(String id) {
        return null;
    }

    @Override
    public List<PatientDto> allPatients() {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        PatientMapper mapper = PatientMapper.getInstance();
        List<PatientDto> dtos = current.getPatients().stream()
                .map(patient ->  mapper.toDto(patient)
                ).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public List<PatientDto> findPatientsByCriteria(String search) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        List<Patient> currentPats = current.getPatients();
        PatientMapper mapper = PatientMapper.getInstance();
        List<Patient> patients = patientRepository.findByNameContainingOrEmailContaining(search,search).stream()
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
