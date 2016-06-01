package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.DashboardUserTemplateResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/private")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @RequestMapping(value = "/dashboard/template", method = RequestMethod.GET)
    @ResponseBody
    public DashboardUserTemplateResponse listTemplates() {
        DashboardUserTemplateResponse response = new DashboardUserTemplateResponse();
        response.setResult(templateService.getAllTemplates());
        response.setState(new StateDto(true, "All templates"));
        return response;
    }
}
