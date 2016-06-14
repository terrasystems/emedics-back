package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.model.mapping.EventMapper;
import com.terrasystems.emedics.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "rest/private/dashboard/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public DashboardEventResponse getAllTasks() {
        DashboardEventResponse response = new DashboardEventResponse();
        StateDto state = new StateDto();
        EventMapper mapper = EventMapper.getInstance();
        List<EventDto> events = taskService.getAllTasks().stream()
                .map(event -> {
                    EventDto dto = new EventDto();
                    try {
                        dto = mapper.toDto(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //dto.setTemplate(null);
                    return dto;
                })
                .collect(Collectors.toList());

        response.setResult(events);
        state.setMessage("All Tasks");
        state.setValue(true);
        response.setState(state);
        return response;
    }
    //TODO refactor this code
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse createTask(@RequestBody EventCreateRequest request) {
        DashboardEventResponse response = new DashboardEventResponse();
        EventMapper mapper = EventMapper.getInstance();
        StateDto state = new StateDto();
        Event event = taskService.createTask(request.getTemplate(), request.getPatient());
        if (event == null) {
            state.setValue(false);
            state.setMessage("You already have task with this template");
            response.setState(state);
            return response;
        }
        try {
            response.setResult(mapper.toDto(event));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getResult()==null) {
            state.setValue(false);
            state.setMessage("Task didnt created");
            response.setState(state);
            return response;
        }
        state.setMessage("Task Created");
        state.setValue(true);
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardEventResponse getTask(@PathVariable String id) {
        EventMapper mapper = EventMapper.getInstance();
        StateDto state = new StateDto();
        DashboardEventResponse response = new DashboardEventResponse();
        try {
            response.setResult(mapper.toDto(taskService.getTask(id)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        state.setMessage("Task selected");
        state.setValue(true);
        response.setState(state);
        return response;
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse editTask(@RequestBody EventEditRequest request) {
        DashboardEventResponse response = new DashboardEventResponse();
        EventMapper mapper = EventMapper.getInstance();
        StateDto state = new StateDto();
        Event event = taskService.editTask(request.getEvent());
        if (event == null) {
            state.setMessage("Failed edit Task");
            state.setValue(false);
            response.setState(state);
            return response;
        }
        state.setMessage("Task Edited");
        state.setValue(true);
        response.setState(state);
        try {
            response.setResult(mapper.toDto(event));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
