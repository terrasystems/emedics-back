package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.services.DoctorPatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "rest/private/dashboard/")
public class DoctorPatientsController {
    @Autowired
    DoctorPatientsService doctorPatientsService;

    @RequestMapping(value = "/docpatients", method = RequestMethod.GET)
    @ResponseBody
    public DashboardPatientsResponse patientsAll() {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        StateDto state = new StateDto();
        List<PatientDto> result = doctorPatientsService.allPatients();
        state.setMessage("All patients");
        state.setValue(true);
        response.setState(state);
        response.setResult(result);
        return response;
    }
    @RequestMapping(value = "/docpatients/search", method = RequestMethod.POST)
    @ResponseBody
    public DashboardPatientsResponse patientsSearch(@RequestBody DashboardPatientsRequest request) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        StateDto state = new StateDto();
        List<PatientDto> result = doctorPatientsService.findPatientsByCriteria(request.getCriteria().getSearch());
        state.setMessage("All patients");
        state.setValue(true);
        response.setState(state);
        response.setResult(result);
        return response;
    }
    @RequestMapping(value = "/docpatients/add")
    @ResponseBody
    public DashboardPatientsResponse patientsAdd(@RequestBody DashboardPatientsRequest request) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        StateDto state = doctorPatientsService.patientAdd(request.getCriteria().getList().get(0).getId());
        response.setState(state);
        return response;
    }
    @RequestMapping(value = "/docpatients/forms/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardPatientsResponse getHistoryById(@PathVariable String id) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        StateDto state = new StateDto();
        response.setResult(doctorPatientsService.getPatientHistory(id));
        state.setMessage(MessageEnums.MSG_FORM.toString());
        state.setValue(true);
        response.setState(state);
        return response;
    }
    @RequestMapping(value = "/docpatients/forms/edit", method = RequestMethod.POST)
    @ResponseBody
    public DashboardPatientsResponse editHistory(@RequestBody HistoryDto history) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        StateDto state = doctorPatientsService.editPatientHistory(history);
        response.setState(state);
        return response;
    }
    @RequestMapping(value = "/docpatients/remove", method = RequestMethod.POST)
    @ResponseBody
    public DashboardPatientsResponse removePatient(@RequestBody DashboardPatientsRequest request) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        StateDto state = doctorPatientsService.removePatient(request.getCriteria().getList().get(0).getId());
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/docpatients/invite", method = RequestMethod.POST)
    @ResponseBody
    public DashboardPatientsResponse inviteReference(@RequestBody String email) {
        DashboardPatientsResponse response = new DashboardPatientsResponse();

        StateDto status = new StateDto();
        status.setMessage(MessageEnums.MSG_INVITE.toString());
        status.setValue(true);
        return response;
    }

}
