package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.dto.DashboardFormsRequest;
import com.terrasystems.emedics.model.dto.ListDashboardFormsResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ListDashboardFormsResponse modifyActiveForms(@RequestBody DashboardFormsRequest req) {
       /* List<Form> list = patientDashboardService.changeActiveForms(req.getForms());
        return  new ListDashboardFormsResponse(list, new StateDto());*/
        return null;
    }
    @RequestMapping(value = "/forms", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardFormsResponse formsGetAll(@RequestBody DashboardFormsRequest request) {
        ListDashboardFormsResponse response = new ListDashboardFormsResponse();
        List<Form> list = patientDashboardService.getAllForms();
        StateDto state = new StateDto();
        if (list != null) {
            state.setValue(true);
            state.setMessage("All Forms");
        }
        else {
            state.setValue(false);
            state.setMessage("Cant get forms");
        }
        response.setList(list);
        response.setState(state);
        return response;
    }
}
