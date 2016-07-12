package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.model.mapping.EventMapper;
import com.terrasystems.emedics.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "rest/private/dashboard/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse getAllTasks(@RequestBody TaskSearchCriteria criteria) {
        DashboardEventResponse response = new DashboardEventResponse();
        StateDto state = new StateDto();
        EventMapper mapper = EventMapper.getInstance();
        List<EventDto> events = taskService.getAllTasks(criteria).stream()
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

    @RequestMapping(value = "/gethistory", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse getHistory(@RequestBody TaskSearchCriteria criteria) {
        DashboardEventResponse response = new DashboardEventResponse();
        StateDto state = new StateDto();
        EventMapper mapper = EventMapper.getInstance();
        List<EventDto> events = taskService.getHistory(criteria).stream()
                .map(event -> {
                    EventDto dto = new EventDto();
                    try {
                        dto = mapper.toDto(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return dto;
                }).collect(Collectors.toList());

        response.setResult(events);
        state.setMessage("History");
        state.setValue(true);
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/close/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardEventResponse closeTask(@PathVariable String id) {
        DashboardEventResponse response = new DashboardEventResponse();
        response.setState(taskService.closeTask(id));
        return response;
    }

    //TODO refactor this code
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse createTask(@RequestBody EventCreateRequest request) {
        if (request.getFromId() != null) {
            User current = userRepository.findOne(request.getFromId());
            if (current == null) {
                DashboardEventResponse response = new DashboardEventResponse();
                response.setState(new StateDto(false, "User with such id doesn't exist"));
                return response;
            } else {
                Event event = taskService.createTask(request.getTemplate(), request.getPatient(), request.getFromId(), request.getData());
                return createTaskLogic(event);
            }
        } else {
            User current = userRepository.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            Event event = taskService.createTask(request.getTemplate(), request.getPatient(), current.getId(), request.getData());
            return createTaskLogic(event);
        }
    }

    DashboardEventResponse createTaskLogic(Event event) {
        DashboardEventResponse response = new DashboardEventResponse();
        EventMapper mapper = EventMapper.getInstance();
        StateDto state = new StateDto();
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
        if (response.getResult() == null) {
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


    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse getTasksByCriteria(@RequestBody TaskSearchCriteria criteria) {
        DashboardEventResponse response = new DashboardEventResponse();
        StateDto state = new StateDto();
        EventMapper mapper = EventMapper.getInstance();

        List<EventDto> events = taskService.getByCriteria(criteria).stream()
                .map(event -> {
                    EventDto dto = new EventDto();
                    try {
                        dto = mapper.toDto(event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dto.setTemplate(null);
                    return dto;
                })
                .collect(Collectors.toList());

        response.setResult(events);
        state.setMessage("Tasks by filters");
        state.setValue(true);
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/multipleCreate", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse multipleSend(@RequestBody EventSendMultiRequest request) {
        DashboardEventResponse response = new DashboardEventResponse();
        response.setState(taskService.multiCreateTask(request.getTemplate(), request.getPatients(), request.getMessage(), request.isAssignAll()));
        return response;
    }

    @RequestMapping(value = "/findTask/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardEventResponse findTask(@PathVariable String id) {
        DashboardEventResponse response = new DashboardEventResponse();
        EventMapper mapper = EventMapper.getInstance();
        Event event = taskService.findUserTask(id);
        StateDto stateDto = new StateDto();
        if(event == null) {
            stateDto.setValue(true);
            stateDto.setMessage("Task don't exist");
            response.setState(stateDto);
            return response;
        }
        EventDto eventDto = null;
        try {
            eventDto = mapper.toDto(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stateDto.setValue(true);
        stateDto.setMessage("Task is exist");
        response.setState(stateDto);
        response.setResult(eventDto);
        return response;
    }
    @RequestMapping(value = "/syncTasks", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse synkTasks(@RequestBody String tasks) {
        DashboardEventResponse response = new DashboardEventResponse();
        StateDto state = new StateDto();
        response.setResult(tasks);
        return response;
    }
}
