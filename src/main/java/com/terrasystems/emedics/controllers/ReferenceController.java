/*
package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.dto.DashboardReferenceResponse;
import com.terrasystems.emedics.model.dto.DashboardReferenceRequest;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/private/dashboard/patient")
public class ReferenceController {

    @Autowired
    ReferenceService referenceService;


    @RequestMapping(value = "/references/edit", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse referenceEdit(@RequestBody ReferenceDto request) {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        Reference reference = referenceService.editReference(request);
        List<ReferenceDto> references = new ArrayList<>();
        StateDto state = new StateDto();
        if(reference == null) {
            state.setMessage(MessageEnums.MSG_ERROR.toString());
            state.setValue(false);
        } else {
            state.setMessage(MessageEnums.MSG_EDITE.toString());
            state.setValue(true);
            references.add(new ReferenceDto(reference.getId(), reference.getName(), reference.getType(), reference.getPhone()));
        }

        response.setState(state);
        response.setResult(references.get(0));
        return response;
    }

    @RequestMapping(value = "/references", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse referencesGetAll(@RequestBody DashboardReferenceRequest request) {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        List<ReferenceDto> references = referenceService.getAllReferences().stream()
                .map(item -> {
                    ReferenceDto reference = new ReferenceDto();
                    reference.setName(item.getName());
                    reference.setPhone(item.getPhone());
                    reference.setType(item.getType());
                    reference.setId(item.getId());
                    return reference;
                }).collect(Collectors.toList());
        response.setResult(references);
        response.setState(new StateDto(true, "All responces"));
        return response;
    }

    @RequestMapping(value = "/references/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardReferenceResponse referenceRemove(@PathVariable String id) throws IOException {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        if(referenceService.getReferenceById(id) == null ) {
            response.setState(new StateDto(false, "Reference doesn't exist"));
        } else {
            referenceService.removeReference(id);
            response.setState(new StateDto(true, "Reference remove"));
        }


        return response;
    }
}
*/
