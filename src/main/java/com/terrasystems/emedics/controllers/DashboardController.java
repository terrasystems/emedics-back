package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.dao.FormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Blank;
import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
                    BlankDto blank = new BlankDto();
                    blank.setDescr(item.getBlank().getDescr());
                    blank.setName(item.getBlank().getName());
                    blank.setBody(null);
                    blank.setCategory(item.getBlank().getCategory());
                    blank.setNumber(item.getBlank().getNumber());
                    blank.setType(item.getBlank().getType());
                    form.setBlank(blank);
                    form.setActive(item.isActive());
                    form.setId(item.getId());
                    form.setData(item.getData());
                    return form;
                })
                .collect(Collectors.toList());
        response.setResult(list);

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
                    BlankDto blank = new BlankDto();
                    blank.setDescr(item.getBlank().getDescr());
                    blank.setName(item.getBlank().getName());
                    blank.setBody(null);
                    blank.setCategory(item.getBlank().getCategory());
                    blank.setNumber(item.getBlank().getNumber());
                    form.setBlank(blank);
                    form.setActive(item.isActive());
                    form.setId(item.getId());
                    form.setData(item.getData());
                    return form;
                }).collect(Collectors.toList());
        response.setState(new StateDto(true,"All Forms"));
        response.setResult(forms);
        return  response;
    }

    @RequestMapping(value = "forms/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ListDashboardFormsResponse formsGetById(@PathVariable String id) {
        ListDashboardFormsResponse response = new ListDashboardFormsResponse();
        Form form = patientDashboardService.getFormById(id);
        BlankDto blank = new BlankDto();
        blank.setBody(form.getBlank().getBody());
        blank.setName(form.getBlank().getName());
        blank.setDescr(form.getBlank().getDescr());
        blank.setNumber(form.getBlank().getNumber());
        blank.setType(form.getBlank().getType());
        blank.setCategory(form.getBlank().getCategory());


        if (form != null) {

            response.setState(new StateDto(true, "Form by id"));
            response.setResult(new FormDto(form.getId(),form.getData(),blank));
        } else {
            response.setState(new StateDto(false, "Form with such id doesnt exist"));
            response.setResult(null);
        }

        return response;
    }

    @RequestMapping(value = "/forms/edit", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardFormsResponse formsEdit(@RequestBody FormDto request) {
        ListDashboardFormsResponse response = new ListDashboardFormsResponse();
        Form form = patientDashboardService.editForm(request);
        List<FormDto> forms = new ArrayList<>();
        StateDto state = new StateDto();
        if (form == null) {
            state.setMessage("error");
            state.setValue(false);
        } else {
            state.setMessage("Edited");
            state.setValue(true);
            forms.add(new FormDto(form.getId(),form.getData(), null));
        }


        response.setState(state);
        response.setResult(forms);
        return response;
    }
}
