package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TaskDto;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    ResponseDto createTask(TaskDto taskDto);
    ResponseDto getTaskById(String id) throws IOException;
    ResponseDto editTask(TaskDto taskDto);
    ResponseDto closeTask(String id);
    ResponseDto multiCreateTask(List<TaskDto> taskDtos);
    ResponseDto sendTask(TaskDto taskDto);
}
