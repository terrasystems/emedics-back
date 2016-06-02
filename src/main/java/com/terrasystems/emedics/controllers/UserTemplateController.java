package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.DashboardUserTemplateResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.UserTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/private")
public class UserTemplateController {

    @Autowired
    UserTemplateService userTemplateService;

    @RequestMapping(value = "/dashboard/template_user", method = RequestMethod.GET)
    @ResponseBody
    public DashboardUserTemplateResponse listUserTemplates() {
        DashboardUserTemplateResponse response = new DashboardUserTemplateResponse();
        response.setResult(userTemplateService.getAllTemplates());
        response.setState(new StateDto(true, "All user templates"));
        return response;
    }

    @RequestMapping(value = "/dashboard/template_user/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public StateDto deleteUserTemplate(@PathVariable String id) {
        return userTemplateService.deleteUserTemplateById(id);
    }
}

