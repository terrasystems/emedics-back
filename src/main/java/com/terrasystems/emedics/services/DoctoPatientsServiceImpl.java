package com.terrasystems.emedics.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.dao.HistoryRepository;
import com.terrasystems.emedics.dao.PatientRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.HistoryDto;
import com.terrasystems.emedics.model.dto.PatientDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserFormDto;
import com.terrasystems.emedics.model.mapping.FormMapper;
import com.terrasystems.emedics.model.mapping.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class DoctoPatientsServiceImpl implements DoctorPatientsService, CurrentUserService {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HistoryRepository historyRepository;

    @Override
    public StateDto patientAdd(String id) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        Patient patient = patientRepository.findOne(id);
        StateDto state = new StateDto();
        if (patient == null) {
            state.setValue(false);
            state.setMessage(MessageEnums.MSG_PATS_NOT_EXIST.toString());
            return state;
        }
        current.getPatients().add(patient);
        userRepository.save(current);
        state.setValue(true);
        state.setMessage(MessageEnums.MSG_PAT_ADD.toString());
        return state;
    }

    @Override
    public PatientDto getPatientById(String id) {
        return null;
    }

    /*@Override
    public List<PatientDto> allPatients()  {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        PatientMapper mapper = PatientMapper.getInstance();
        FormMapper formMapper = FormMapper.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        List<PatientDto> dtos = current.getPatients().stream()
                .map(patient ->  {
                    PatientDto patientDto = mapper.toDto(patient);
                    List<History> patientHistory = historyRepository.findByUserForm_User_Id(patient.getId());
                    List<UserFormDto> patientForms = patientHistory.stream()
                            .map(history -> {
                                UserFormDto dto = null;
                                try {
                                    dto = formMapper.toDto(history.getUserForm());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    dto.setData(objectMapper.readTree(history.getData()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return dto;
                            })
                            .collect(Collectors.toList());
                    patientDto.setForms(patientForms);
                    return patientDto;
                }).collect(Collectors.toList());
        return dtos;
    }*/
    @Override
    public List<PatientDto> allPatients() {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        PatientMapper mapper = PatientMapper.getInstance();
        FormMapper formMapper = FormMapper.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        List<PatientDto> dtos = current.getPatients().stream()
                .map(patient -> {
                    PatientDto patientDto = mapper.toDto(patient);
                    List<History> patientHistory = historyRepository.findByUserForm_User_Id(patient.getId());
                    List<HistoryDto> historyDtos = patientHistory.stream()
                            .map(history -> {
                                HistoryDto historyDto = new HistoryDto();
                                try {
                                    historyDto.setData(objectMapper.readTree(history.getData()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                historyDto.setId(history.getId());
                                try {
                                    historyDto.setForm(formMapper.toDto(history.getUserForm()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                historyDto.getForm().getBlank().setBody(null);
                                historyDto.getForm().setData(null);
                                historyDto.setDate(history.getDate());
                                return historyDto;
                            }).collect(Collectors.toList());
                    patientDto.setHistory(historyDtos);
                    return patientDto;
                }).collect(Collectors.toList());
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
    @Override
    public HistoryDto getPatientHistory(String id) {
        FormMapper formMapper = FormMapper.getInstance();
        ObjectMapper objectMapper = new ObjectMapper();
        History history = historyRepository.findOne(id);
        HistoryDto dto = new HistoryDto();
        dto.setDate(history.getDate());
        try {
            dto.setData(objectMapper.readTree(history.getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dto.setForm(formMapper.toDto(history.getUserForm()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dto.getForm().setData(null);


        return dto;
    }

    @Override
    public StateDto editPatientHistory(HistoryDto dto) {
        StateDto state = new StateDto();
        History history =  historyRepository.findOne(dto.getId());
        history.setDate(new Date());
        history.setData(dto.getData().toString());
        historyRepository.save(history);
        state.setValue(true);
        state.setMessage(MessageEnums.MSG_FORM_EDIT.toString());
        return state;
    }

    @Override
    public StateDto removePatient(String id) {
        Doctor current = (Doctor) userRepository.findByEmail(getPrincipals());
        Patient patient = (Patient) userRepository.findOne(id);
        current.getPatients().remove(patient);
        userRepository.save(current);
        StateDto state = new StateDto();
        state.setMessage(MessageEnums.MSG_PAT_REMOVE.toString());
        state.setValue(true);
        return state;
    }


}
