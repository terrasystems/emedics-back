package com.terrasystems.emedics.services;


import com.fasterxml.jackson.databind.JsonNode;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.*;

import java.io.IOException;
import java.util.List;

public interface TaskService {
    Event createTask(UserTemplateDto userTemplate, String patientId, String fromId, String data);
    List<Event> getAllTasks(TaskSearchCriteria criteria);
    Event getTask(String id);
    Event editTask(EventDto eventDto);
    List<Event> getHistory(TaskSearchCriteria criteria);
    StateDto closeTask(String id);
    /*List<Event> getTasksByTemplate(String id);
    List<Event> getTasksByFromUserId(String id);
    List<Event> getTasksByPatient(String id);*/
    List<Event> getByCriteria(TaskSearchCriteria criteria);
    StateDto multiCreateTask(String eventId, List<String> toUsers, String message, boolean assignAll);
    Event findUserTask(String id);
    void syncTasks(List<JsonNode> events) throws IOException;
}
