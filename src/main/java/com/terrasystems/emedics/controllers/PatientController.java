package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.model.dto.DashboardPatientEventResponse;
import com.terrasystems.emedics.model.dto.DashboardPatientsResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.EventPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/private/dashboard/")
public class PatientController {
    @Autowired
    EventPatientService eventPatientService;

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    @ResponseBody
    public DashboardPatientsResponse getPatients() {
        DashboardPatientsResponse response = new DashboardPatientsResponse();
        response.setResult(eventPatientService.getAllPatients());
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
}
