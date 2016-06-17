package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.DocType;
import com.terrasystems.emedics.model.dto.DocTypeDto;
import com.terrasystems.emedics.model.dto.ObjectResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.DocTypeMapper;
import com.terrasystems.emedics.model.mapping.DoctorMapper;
import com.terrasystems.emedics.services.DocTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/private/dashboard/doc_type")
public class DocTypeController {

    @Autowired
    DocTypeService docTypeService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ObjectResponse getAllDocTypes() {
        ObjectResponse response = new ObjectResponse();
        List<DocTypeDto> docTypeDtos = docTypeService.getAll().stream()
                .map(item -> {
                    DocTypeMapper mapper = DocTypeMapper.getInstance();
                    DocTypeDto docTypeDto = new DocTypeDto();
                    docTypeDto = mapper.toDto(item);
                    return docTypeDto;
                }).collect(Collectors.toList());

        response.setState(new StateDto(true, "All DocTypes"));
        response.setResult(docTypeDtos);
        return response;
    }

    @RequestMapping(value = "/doctor", method = RequestMethod.GET)
    @ResponseBody
    public ObjectResponse getByDoctorDocTypes() {
        ObjectResponse response = new ObjectResponse();
        List<DocTypeDto> docTypeDtos = docTypeService.getByValueDoctor().stream()
                .map(item -> {
                    DocTypeMapper mapper = DocTypeMapper.getInstance();
                    DocTypeDto docTypeDto = new DocTypeDto();
                    docTypeDto = mapper.toDto(item);
                    return docTypeDto;
                }).collect(Collectors.toList());

        response.setState(new StateDto(true, "DocTypes by doctor"));
        response.setResult(docTypeDtos);
        return response;
    }

    @RequestMapping(value = "/organization", method = RequestMethod.GET)
    @ResponseBody
    public ObjectResponse getByOrganizationDocType() {
        ObjectResponse response = new ObjectResponse();
        List<DocTypeDto> docTypeDtos = docTypeService.getByValueOrganization().stream()
                .map(item -> {
                    DocTypeMapper mapper = DocTypeMapper.getInstance();
                    DocTypeDto docTypeDto = new DocTypeDto();
                    docTypeDto = mapper.toDto(item);
                    return docTypeDto;
                }).collect(Collectors.toList());
        response.setState(new StateDto(true, "DocTypes by "));
        response.setResult(docTypeDtos);
        return response;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResponse createDocType(@RequestBody DocTypeDto docTypeDto) {
        ObjectResponse response = new ObjectResponse();
        response.setState(docTypeService.addDocType(docTypeDto));
        return response;
    }
}
