package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.model.dto.DashboardReferenceRequest;
import com.terrasystems.emedics.model.dto.DashboardReferenceResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/private/dashboard/patient")
public class PatientReferencesController {

    @Autowired
    @Qualifier("patientReferenceService")
    ReferenceService referenceService;

    @RequestMapping(value = "/references/search", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse searchReferences(@RequestBody DashboardReferenceRequest request) {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        response.setResult(referenceService.findAllReferencesByCriteria(request.getCriteria().getSearch()));
        response.setState(new StateDto(true, "Search result"));
        return response;
    }


}
