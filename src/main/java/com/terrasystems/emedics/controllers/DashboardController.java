package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.dto.DashboardFormsRequest;
import com.terrasystems.emedics.model.dto.ListDashboardFormsResponse;
import com.terrasystems.emedics.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/private/dashboard/patient")
public class DashboardController {
    @Autowired
    FormRepository formRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DashboardService patientDashboardService;

    @RequestMapping(value = "/forms/active", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardFormsResponse getActiveForms(@RequestBody DashboardFormsRequest request) {



        return patientDashboardService.getActiveForms();
    }

    @RequestMapping(value = "/forms/active/modify", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardFormsResponse modifyActiveForms(@RequestBody DashboardFormsRequest req) {


        return patientDashboardService.changeActiveForms(req.getCriteria().getList());

    }
    @RequestMapping(value = "/forms", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardFormsResponse formsGetAll(@RequestBody DashboardFormsRequest request) {

        return  patientDashboardService.getAllForms();
    }
}
