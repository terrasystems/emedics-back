package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.UserTemplateDto;

import java.util.List;

public interface TaskService {
    Event createTask(UserTemplateDto userTemplate);
    List<Event> getAllTasks();
    Event getTask(String id);
    Event editTask();
}
