package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.model.Stuff;
import com.terrasystems.emedics.model.dto.ObjectResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.StuffDto;
import com.terrasystems.emedics.model.mapping.StuffMapper;
import com.terrasystems.emedics.services.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/private/dashboard/")
public class StuffController {
    @Autowired
    StuffService stuffService;

    @RequestMapping(value = "/stuff", method = RequestMethod.GET)
    @ResponseBody
    public ObjectResponse getStuff() {
        StuffMapper mapper = StuffMapper.getInstance();
        ObjectResponse response = new ObjectResponse();
        List<StuffDto> stuffDtos = stuffService.getAllStuff().stream()
                .map(stuff -> mapper.toDto(stuff))
                .collect(Collectors.toList());
        response.setResult(stuffDtos);
        response.setState(new StateDto(true, "All stuff"));
        return response;
    }

    @RequestMapping (value = "stuff/create", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResponse createStuff(@RequestBody StuffDto stuffDto) {
        StuffMapper mapper = StuffMapper.getInstance();
        ObjectResponse response = new ObjectResponse();
        StateDto status = new StateDto();
        Stuff stuff = stuffService.createStuff(stuffDto);
        if (stuff != null) {
            StuffDto dto = mapper.toDto(stuff);
            status.setValue(true);
            status.setMessage("Stuff created");
            response.setResult(dto);
            response.setState(status);
            return response;
        }
        status.setValue(false);
        status.setMessage("Error creating stuff. Check email is correct");
        response.setState(status);
        return response;
    }

    /*@RequestMapping(value = "/stuff/{id}", Reqq)*/

}
