package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.services.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/rest/private/dashboard/patient","/rest/private/dashboard/doctor"})

public class PatientReferencesController {

    @Autowired
    @Qualifier("patientReferenceService")
    ReferenceService referenceService;

    @RequestMapping(value = "/references/search", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse searchReferences(@RequestBody DashboardReferenceRequest request) {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        response.setResult(referenceService.findAllReferencesByCriteria(request.getCriteria().getSearch()));
        response.setState(new StateDto(true, MessageEnums.MSG_SEARCH.toString()));
        return response;
    }
    @RequestMapping(value = "/references/add", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse addReferences(@RequestBody DashboardReferenceRequest request) {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        StateDto state = referenceService.addReferences(request.getCriteria().getList());
        response.setState(state);
        return response;
    }
    @RequestMapping(value = "/references", method = RequestMethod.GET)
    @ResponseBody
    public DashboardReferenceResponse getAllReferences() {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        List<ReferenceDto> references = (List<ReferenceDto>) referenceService.getAllReferences();
        StateDto state = new StateDto();
        if (references == null) {
            state.setValue(false);
            state.setMessage(MessageEnums.MSG_CANT_GET_REFS.toString());
        } else {
            state.setValue(true);
            state.setMessage(MessageEnums.MSG_ALL_REFS.toString());
        }
        response.setResult(references);
        response.setState(state);
        return response;
    }
    @RequestMapping(value = "/references/remove", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse removeReferences(@RequestBody DashboardReferenceRequest request) {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        StateDto state = new StateDto();
        try {
            referenceService.removeReferences(request.getCriteria().getList());
            state.setMessage(MessageEnums.MSG_REMOVE_REF.toString());
            state.setValue(true);
        } catch (Exception e) {
            e.printStackTrace();
            state.setMessage(MessageEnums.MSG_CANT_REMOVE_REF.toString());
            state.setValue(false);
        }
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/references/create", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse createReference(@RequestBody String email) {
        StateDto state = referenceService.createReference(email);
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/references/invite", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse inviteReference(@RequestBody String email) {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        StateDto status = new StateDto();
        status.setMessage(MessageEnums.MSG_INVITE.toString());
        status.setValue(true);
        return response;
    }

    @RequestMapping(value = "/references/refs", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse getMyRefs(@RequestBody MyRefsRequest request) {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        StateDto status = new StateDto();
        response.setResult(referenceService.findMyRefs(request.getSearch(),request.getType()));
        status.setMessage("Refs");
        status.setValue(true);
        response.setState(status);
        return response;
    }

}
