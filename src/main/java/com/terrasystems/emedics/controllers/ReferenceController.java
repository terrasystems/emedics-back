package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.dto.DashboardReferenceResponse;
import com.terrasystems.emedics.model.dto.ReferenceDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/private/dashboard")
public class ReferenceController {

    @Autowired
    ReferenceService referenceService;


    @RequestMapping(value = "/references/edit", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse responsesEdit(@RequestBody ReferenceDto request) {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        Reference reference = referenceService.editReference(request);
        List<ReferenceDto> references = new ArrayList<>();
        StateDto state = new StateDto();
        if(reference == null) {
            state.setMessage("error");
            state.setValue(false);
        } else {
            state.setMessage("Edited");
            state.setValue(true);
            references.add(new ReferenceDto(reference.getId(), reference.getName(), reference.getType(), reference.getPhone()));
        }

        response.setState(state);
        response.setObject(references);
        return response;
    }

    @RequestMapping(value = "/references", method = RequestMethod.POST)
    @ResponseBody
    public DashboardReferenceResponse responcesGetAll() {
        DashboardReferenceResponse response = new DashboardReferenceResponse();
        List<ReferenceDto> references = referenceService.getAllReferences().stream()
                .map(item -> {
                    ReferenceDto reference = new ReferenceDto();
                    reference.setName(item.getName());
                    reference.setPhone(item.getPhone());
                    reference.setType(item.getType());
                    return reference;
                }).collect(Collectors.toList());
        response.setObject(references);
        response.setState(new StateDto(true, "All responces"));
        return response;
    }
}
