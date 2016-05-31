package com.terrasystems.emedics.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.dao.UserFormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.UserForm;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/private/dashboard/doctor")
public class DoctorDashboardController {
    @Autowired
    UserFormRepository userFormRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DashboardService doctorDashboardService;

    @RequestMapping(value = "/forms", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardUserFormsResponse userFormsGetAll(@RequestBody DashboardUserFormsRequest request) {
        ListDashboardUserFormsResponse response = new ListDashboardUserFormsResponse();
        ObjectMapper mapper = new ObjectMapper();
        List<UserFormDto> userForms = doctorDashboardService.getAllUserForms().stream()
                .map(item -> {
                    UserFormDto userForm = new UserFormDto();
                    BlankDto blank = new BlankDto();
                    blank.setDescr(item.getBlank().getDescr());
                    blank.setName(item.getBlank().getName());
                    blank.setBody(null);
                    blank.setCategory(item.getBlank().getCategory());
                    blank.setNumber(item.getBlank().getNumber());
                    userForm.setBlank(blank);
                    userForm.setActive(item.isActive());
                    userForm.setId(item.getId());
                    try {
                        userForm.setData(mapper.readTree(item.getData()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return userForm;
                }).collect(Collectors.toList());
        response.setState(new StateDto(true,"MSG_ALL_FORMS"));
        response.setResult(userForms);
        return  response;
    }

    @RequestMapping(value = "forms/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ListDashboardUserFormsResponse userFormsGetById(@PathVariable String id) throws IOException {
        ListDashboardUserFormsResponse response = new ListDashboardUserFormsResponse();
        UserForm userForm = doctorDashboardService.getUserFormById(id);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode body = null;
        try {
            body = mapper.readTree(userForm.getBlank().getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BlankDto blank = new BlankDto();
        blank.setBody(body);
        blank.setName(userForm.getBlank().getName());
        blank.setDescr(userForm.getBlank().getDescr());
        blank.setNumber(userForm.getBlank().getNumber());
        blank.setType(userForm.getBlank().getType());
        blank.setCategory(userForm.getBlank().getCategory());


        if (userForm != null) {

            response.setState(new StateDto(true, "UserForm by id"));
            UserFormDto userFormDto = new UserFormDto(userForm.getId(),mapper.readTree(userForm.getData()),blank);
            userFormDto.setActive(userForm.isActive());
            response.setResult(userFormDto);
        } else {
            response.setState(new StateDto(false, "UserForm with such id doesn't exist"));
            response.setResult(null);
        }

        return response;
    }

    @RequestMapping(value = "/forms/edit", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardUserFormsResponse UserFormsEdit(@RequestBody UserFormDto request) {
        ListDashboardUserFormsResponse response = new ListDashboardUserFormsResponse();
        UserForm userForm = doctorDashboardService.editUserForm(request);
        List<UserFormDto> userForms = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        StateDto state = new StateDto();
        if (userForm == null) {
            state.setMessage("MSG_ERROR");
            state.setValue(false);
        } else {
            state.setMessage("MSG_EDITE");
            state.setValue(true);
            try {
                userForms.add(new UserFormDto(userForm.getId(),mapper.readTree(userForm.getData()), null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        response.setState(state);
        response.setResult(userForms);
        return response;
    }

    @RequestMapping(value = "/forms/active", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardUserFormsResponse userFormsGetActive(@RequestBody DashboardUserFormsRequest request) {
        ListDashboardUserFormsResponse response = new ListDashboardUserFormsResponse();
        response.setState(new StateDto(true, "MSG_ACTIVE_FORMS"));
        ObjectMapper mapper = new ObjectMapper();
        List<UserFormDto> list = doctorDashboardService.getActiveUserForms().stream()
                .map(item -> {
                    UserFormDto form = new UserFormDto();
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
                    try {
                        form.setData(mapper.readTree(item.getData()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return form;
                })
                .collect(Collectors.toList());
        response.setResult(list);

        return response ;
    }

    @RequestMapping(value = "/forms/active/modify", method = RequestMethod.POST)
    @ResponseBody
    public ListDashboardUserFormsResponse userFormsActiveModify(@RequestBody DashboardUserFormsRequest req) {


        return doctorDashboardService.changeActiveUserForms(req.getCriteria().getList());

    }
}
