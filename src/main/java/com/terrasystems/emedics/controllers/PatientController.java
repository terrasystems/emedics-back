package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.model.mapping.PatientMapper;
import com.terrasystems.emedics.services.EventPatientService;
import com.terrasystems.emedics.services.PatientReferenceServiceImpl;
import com.terrasystems.emedics.services.ReferenceCreateService;
import com.terrasystems.emedics.services.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/private/dashboard/")
public class PatientController {
    @Autowired
    EventPatientService eventPatientService;
    @Autowired
    ReferenceCreateService referenceCreateService;
    @Autowired
    ReferenceService referenceService;


    @RequestMapping(value = "/patients", method = RequestMethod.POST)
    @ResponseBody
    public DashboardPatientsResponse getPatients(@RequestBody PatientCriteria criteria) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        PatientMapper mapper = PatientMapper.getInstance();
        List<Patient> patients = eventPatientService.getAllPatients(criteria);
        if (patients == null) {
            response.setState(new StateDto(false,"Not supported yet"));
            return response;
        }
        List<PatientDto> dtos = patients.stream()
                .map(patient -> mapper.toDto(patient))
                .collect(Collectors.toList());
        response.setResult(dtos);
        response.setState(new StateDto(true, "All patients"));
        return response;
    }
    @RequestMapping(value = "/patients/{id}/events")
    public DashboardPatientEventResponse gePatientEvents(@PathVariable String id) {
        DashboardPatientEventResponse response = new DashboardPatientEventResponse();
        response.setResult(eventPatientService.getPatientsEvents(id));
        response.setState(new StateDto(true,"Patient's Forms"));
        return response;
    }
    @RequestMapping(value = "/patients/search", method = RequestMethod.POST)
    @ResponseBody
    public DashboardPatientsResponse patientsSearch(@RequestBody DashboardPatientsRequest request) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        StateDto state = new StateDto();
        List<PatientDto> result = eventPatientService.findPatientByCriteria(request.getCriteria().getSearch());
        state.setMessage("All patients");
        state.setValue(true);
        response.setState(state);
        response.setResult(result);
        return response;
    }
    @RequestMapping(value = "/patients/add")
    @ResponseBody
    public DashboardPatientsResponse patientsAdd(@RequestBody DashboardPatientsRequest request) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        StateDto state = eventPatientService.addPatient(request.getCriteria().getList().get(0).getId());
        response.setState(state);
        return response;
    }
    @RequestMapping(value = "/patients/remove", method = RequestMethod.POST)
    @ResponseBody
    public DashboardPatientsResponse removePatient(@RequestBody DashboardPatientsRequest request) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        StateDto state = eventPatientService.removePatient(request.getCriteria().getList().get(0).getId());
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/patients/create", method = RequestMethod.POST)
    @ResponseBody
    public DashboardPatientsResponse createPatients(@RequestBody ReferenceCreateRequest request) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        String user = referenceService.createReference(request);
        if (user != null) {
            response.setResult(user);
            response.setState(new StateDto(true,"User created"));
            return response;
        } else {
            response.setResult(null);
            response.setState(new StateDto(false, "Email incorrect or user with such email is already exists"));
            return response;
        }
    }
}
