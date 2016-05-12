package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.model.dto.DashboardReferenceRequest;
import com.terrasystems.emedics.model.dto.DashboardReferenceResponse;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping(value = "/references/add", method = RequestMethod.POST)
    @ResponseBody
    public StateDto addReferences(@RequestBody DashboardReferenceRequest request) {
        return referenceService.addReferences(request.getCriteria().getList());
    }
    @RequestMapping(value = "/references", method = RequestMethod.GET)
    @ResponseBody
    public DashboardReferenceResponse getAllReferences() {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        List<ReferenceDto> references = (List<ReferenceDto>) referenceService.getAllReferences();
        StateDto state = new StateDto();
        if (references == null) {
            state.setValue(false);
            state.setMessage("Cant get References");
        } else {
            state.setValue(true);
            state.setMessage("All References");
        }
        response.setResult(references);
        response.setState(state);
        return response;
    }
    @RequestMapping(value = "/references/remove", method = RequestMethod.POST)
    @ResponseBody
    public StateDto removeReferences(@RequestBody DashboardReferenceRequest request) {
        StateDto state = new StateDto();
        try {
            referenceService.removeReferences(request.getCriteria().getList());
            state.setMessage("References Removed");
            state.setValue(true);
        } catch (Exception e) {
            e.printStackTrace();
            state.setMessage("Cant  remove references");
            state.setValue(false);
        }
        return state;
    }


}
