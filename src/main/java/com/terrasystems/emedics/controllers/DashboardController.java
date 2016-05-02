package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.dto.DashboardFormsRequest;
import com.terrasystems.emedics.model.dto.FormDto;
import com.terrasystems.emedics.model.dto.ListDashboardFormsResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ListDashboardFormsResponse formsGetActive(@RequestBody DashboardFormsRequest request) {
        ListDashboardFormsResponse response = new ListDashboardFormsResponse();
        response.setState(new StateDto(true, "Active forms"));
        List<FormDto> list = patientDashboardService.getActiveForms().stream()
                .map(item -> {
                    FormDto form = new FormDto();
                    form.setDescr(item.getBlank().getDescr());
                    form.setActive(item.isActive());
                    form.setId(item.getId());
                    form.setName(item.getBlank().getName());
                    form.setNumber(item.getBlank().getNumber());

                    return form;
                })
                .collect(Collectors.toList());
        response.setList(list);

        return response ;
    }

    @RequestMapping(value = "/forms/active/modify", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardFormsResponse formsActiveModify(@RequestBody DashboardFormsRequest req) {


        return patientDashboardService.changeActiveForms(req.getCriteria().getList());

    }
    @RequestMapping(value = "/forms", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardFormsResponse formsGetAll(@RequestBody DashboardFormsRequest request) {
        ListDashboardFormsResponse response = new ListDashboardFormsResponse();
        List<FormDto> forms = patientDashboardService.getAllForms().stream()
                .map(item -> {
                    FormDto form = new FormDto();
                    form.setDescr(item.getBlank().getDescr());
                    form.setActive(item.isActive());
                    form.setId(item.getId());
                    form.setName(item.getBlank().getName());
                    form.setNumber(item.getBlank().getNumber());
                    return form;
                }).collect(Collectors.toList());
        response.setState(new StateDto(true,"All Forms"));
        response.setList(forms);
        return  response;
    }
}
