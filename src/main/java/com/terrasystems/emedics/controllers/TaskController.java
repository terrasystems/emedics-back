package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.DashboardTaskResponse;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.EventMapper;
import com.terrasystems.emedics.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "rest/private/dashboard/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    @ResponseBody
    public DashboardTaskResponse getAllTasks() {
        DashboardTaskResponse response = new DashboardTaskResponse();
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
                    return dto;
                })
                .collect(Collectors.toList());

        response.setResult(events);
        state.setMessage("All Tasks");
        state.setValue(true);
        response.setState(state);
        return response;
    }
}
