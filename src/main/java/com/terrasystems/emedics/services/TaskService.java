package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.TaskSearchCriteria;
import com.terrasystems.emedics.model.dto.UserTemplateDto;

import java.util.List;

public interface TaskService {
    Event createTask(UserTemplateDto userTemplate, String patientId, String fromId);
    List<Event> getAllTasks(TaskSearchCriteria criteria);
    Event getTask(String id);
    Event editTask(EventDto eventDto);
    List<Event> getHistory(TaskSearchCriteria criteria);
    StateDto closeTask(String id);
    /*List<Event> getTasksByTemplate(String id);
    List<Event> getTasksByFromUserId(String id);
    List<Event> getTasksByPatient(String id);*/
    List<Event> getByCriteria(TaskSearchCriteria criteria);
    StateDto multiSendTask(String eventId, List<String> toUsers, String message);
    Event findUserTask(String id);
}
