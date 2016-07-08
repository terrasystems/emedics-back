package com.terrasystems.emedics.controllers;


import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Stuff;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.model.mapping.EventMapper;
import com.terrasystems.emedics.model.mapping.StuffMapper;
import com.terrasystems.emedics.services.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/private/dashboard/")
public class StuffController {
    @Autowired
    StuffService stuffService;

    @RequestMapping(value = "/stuff", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResponse getStuff(@RequestBody StuffCriteria criteria) {
        StuffMapper mapper = StuffMapper.getInstance();
        ObjectResponse response = new ObjectResponse();
        List<StuffDto> stuffDtos = stuffService.getAllStuff(criteria).stream()
                .map(stuff -> {
                    StuffDto dto = mapper.toDto(stuff);
                    return dto;
                })
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
        status.setMessage("Email incorrect or user with such email is already exist");
        response.setState(status);
        return response;
    }

    @RequestMapping(value = "/stuff/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectResponse getById(@PathVariable String id) {
        StuffMapper mapper = StuffMapper.getInstance();
        ObjectResponse response = new ObjectResponse();
        StateDto status = new StateDto();
        Stuff stuff = stuffService.getById(id);
        if (stuff == null) {
            status.setMessage("No user with such id");
            status.setValue(false);
            response.setState(status);
            return response;
        }
        status.setMessage("User by id");
        status.setValue(true);
        response.setResult(mapper.toDto(stuff));
        response.setState(status);
        return response;
    }
    @RequestMapping(value = "/stuff/edit", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResponse editStuff(@RequestBody StuffDto request) {
        ObjectResponse response = new ObjectResponse();
        StuffMapper mapper = StuffMapper.getInstance();
        StateDto state = new StateDto();
        Stuff stuff = stuffService.updateStuff(request);
        if (stuff == null) {
            state.setValue(false);
            state.setMessage("Error while updating user");
            response.setState(state);
            return response;
        }
        state.setValue(true);
        state.setMessage("User updated");
        response.setState(state);
        response.setResult(mapper.toDto(stuff));
        return response;
    }
    @RequestMapping(value = "/stuff/{id}/events")
    public ObjectResponse getStuffEvents(@PathVariable String id) {
        ObjectResponse response = new ObjectResponse();
        response.setResult(stuffService.getStuffEvents(id));
        response.setState(new StateDto(true,"Stuff's Forms"));
        return response;
    }


    @RequestMapping(value = "stuff/inactiveStuff/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectResponse inactiveStuff(@PathVariable String id) {
        ObjectResponse response = new ObjectResponse();
        response.setState(stuffService.inactiveStuff(id));
        return response;
    }


    @RequestMapping(value = "stuff/assignTask", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResponse assigntask(@RequestBody AssignStuffTaskRequest request) {
        EventMapper mapper = EventMapper.getInstance();
        ObjectResponse response = new ObjectResponse();
        StateDto state = new StateDto();
        Event event = stuffService.assignTask(request.getStuffId(), request.getEventId());
        if (event == null) {
            state.setMessage("Error! Task didn't assigned");
            state.setValue(false);
            response.setState(state);
            return response;
        }
        state.setValue(true);
        state.setMessage("Task Assigned");
        response.setState(state);
        try {
            response.setResult(mapper.toDto(event));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "stuff/event/adminEdit", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResponse adminEdit(@RequestBody EventEditRequest eventEditRequest) {
        if(eventEditRequest.getEvent()!=null) {
            ObjectResponse response = stuffService.editTask(eventEditRequest.getEvent());
            return response;
        } else {
            ObjectResponse response = new ObjectResponse();
            response.setState(new StateDto(false, "Failed edit Task"));
            return response;
        }
    }

    @RequestMapping(value = "stuff/event/adminClose/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectResponse adminClose(@PathVariable String id) {
        ObjectResponse response = new ObjectResponse();
        response.setState(stuffService.closeTask(id));
        return response;
    }

    @RequestMapping(value = "stuff/getOrgPatients", method = RequestMethod.GET)
    @ResponseBody
    public ObjectResponse getOrgPatients(){
        ObjectResponse response = new ObjectResponse();

        response.setResult(stuffService.getAllPatients());
        response.setState(new StateDto(true, "All patients"));
        return response;
    }

}
