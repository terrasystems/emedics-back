package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.services.DashboardService;
import com.terrasystems.emedics.services.PatientDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/private/dashboard/patient")
public class DashboardController {
    @Autowired
    FormRepository formRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DashboardService patientDashboardService;

    @RequestMapping(value = "/forms/active", method = RequestMethod.GET)
    @ResponseBody
    public List<Form> activeForms() {

        return patientDashboardService.getActiveForms();
    }

    @RequestMapping(value = "/forms/active/modify", method = RequestMethod.POST)
    @ResponseBody
    public String modifyActiveForms(List<Integer> forms) {
        return null;
    }
}
