package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.UserTemplateDto;

import java.util.List;

public interface TaskService {
    Event createTask(UserTemplateDto userTemplatem, String patientId);
    List<Event> getAllTasks();
    Event getTask(String id);
    Event editTask(EventDto eventDto);
    List<Event> getHistory();
}
