package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TaskCriteriaDto;
import com.terrasystems.emedics.model.dtoV2.TaskDto;
import com.terrasystems.emedics.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/tasks")
public class TaskControllerV2 {

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllTasks(@RequestBody TaskCriteriaDto taskCriteriaDto) {
        return taskService.getAllTasks(taskCriteriaDto);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getTask(@PathVariable String id) throws IOException {
        return taskService.getTaskById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @RequestMapping(value = "/multi_create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto multiCreateTask(@RequestBody List<TaskDto> taskDtos) {
        return taskService.multiCreateTask(taskDtos);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto editTask(@RequestBody TaskDto taskDto) {
        return taskService.editTask(taskDto);
    }

    @RequestMapping(value = "/close/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto closeTask(@PathVariable String id) {
        return taskService.closeTask(id);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto sendTask(@RequestBody TaskDto taskDto) {
        return taskService.sendTask(taskDto);
    }

    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto assignTask(@RequestBody TaskDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getHistory(@RequestBody TaskCriteriaDto taskCriteriaDto) {
        return taskService.getHistory(taskCriteriaDto);
    }



}
