package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.services.MobileReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/rest/private/mobile/dashboard/patient","/rest/private/mobile/dashboard/doctor", "/rest/private/mobile/dashboard/stuff"})
public class MobileReferenceController {

    @Autowired
    MobileReferenceService mobileReferenceService;

    @RequestMapping(value = "/references/search", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResponse searchReferences(@RequestBody MobileReferenceRequest request) {
        ObjectResponse response = new ObjectResponse();
        response.setResult(mobileReferenceService.findReferencesForMobile(request.getCriteriaDto().getSearch(), request.getCriteriaDto().getStart(), request.getCriteriaDto().getCount()));
        response.setState(new StateDto(true, MessageEnums.MSG_SEARCH.toString()));
        return response;
    }
}
