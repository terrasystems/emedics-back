package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.DashboardTemplateResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.TemplateDto;
import com.terrasystems.emedics.model.dto.UserTemplateDto;
import com.terrasystems.emedics.model.mapping.TemplateMapper;
import com.terrasystems.emedics.model.mapping.UserTemplateMapper;
import com.terrasystems.emedics.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/private")
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @RequestMapping(value = "/dashboard/template", method = RequestMethod.GET)
    @ResponseBody
    public DashboardTemplateResponse listTemplates() {
        DashboardTemplateResponse response = new DashboardTemplateResponse();
        List<TemplateDto> templates = templateService.getAllTemplates().stream()
                .map(item -> {
                    TemplateMapper mapper = TemplateMapper.getInstance();
                    TemplateDto templateDto = new TemplateDto();
                    try {
                        templateDto = mapper.toDto(item);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return templateDto;
                }).collect(Collectors.toList());

        response.setResult(templates);
        response.setState(new StateDto(true, "All templates"));
        return response;
    }

    @RequestMapping(value = "/dashboard/user/template", method = RequestMethod.GET)
    @ResponseBody
    public DashboardTemplateResponse listUserTemplates() {
        DashboardTemplateResponse response = new DashboardTemplateResponse();
        List<UserTemplateDto> userTemplates = templateService.getAllUserTemplates().stream()
                .map(item -> {
                    UserTemplateMapper mapper = UserTemplateMapper.getInstance();
                    UserTemplateDto userTemplateDto = new UserTemplateDto();
                    try {
                        userTemplateDto = mapper.toDto(item);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return userTemplateDto;
                }).collect(Collectors.toList());

        response.setResult(userTemplates);
        response.setState(new StateDto(true, "All user templates"));
        return response;
    }

    @RequestMapping(value = "/dashboard/template/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardTemplateResponse deleteUserTemplate(@PathVariable String id) {
        DashboardTemplateResponse response = new DashboardTemplateResponse();
        response.setState(templateService.deleteUserTemplateById(id));
        return response;
    }

    @RequestMapping(value = "/dashboard/template/pay/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardTemplateResponse payTemplate(@PathVariable String id) {
        DashboardTemplateResponse response = new DashboardTemplateResponse();
        response.setState(templateService.payTemplate(id));
        return response;
    }

    @RequestMapping(value = "/dashboard/template/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardTemplateResponse loadTemplate(@PathVariable String id) {
        DashboardTemplateResponse response = new DashboardTemplateResponse();
        response.setState(templateService.loadTemplate(id));
        return response;
    }

    @RequestMapping(value = "/dashboard/template/preview/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardTemplateResponse previewTemplate(@PathVariable String id) throws IOException {
        DashboardTemplateResponse response = new DashboardTemplateResponse();
        TemplateMapper mapper = TemplateMapper.getInstance();
        TemplateDto templateDto = mapper.toDto(templateService.previewTemplate(id));
        response.setResult(templateDto);
        response.setState(new StateDto(true, "Preview Template"));
        return response;
    }
}
