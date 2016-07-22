package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/tasks")
public class TaskControllerV2 {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllTasks(@RequestBody CriteriaDto criteria) {
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto("id"));
        taskDtos.add(new TaskDto("id"));
        return new ResponseDto(true, "Base msg", taskDtos);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getTask(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new TaskDto("id"));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createTask(@RequestBody TaskDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/multi_create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto multiCreateTask(@RequestBody List<TaskDto> request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto editTask(@RequestBody TaskDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/close/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto closeTask(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto sendTask(@RequestBody TaskDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto assignTask(@RequestBody TaskDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getHistory(@RequestBody CriteriaDto criteria) {
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto("id"));
        taskDtos.add(new TaskDto("id"));
        return new ResponseDto(true, "Base msg", taskDtos);
    }



}
