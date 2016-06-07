package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, String> {
    List<Event> findByToUser_IdAndStatus(String id, StatusEnum status);
    /*boolean existsByPatient_IdAndStatusAndTemplate_Id(String patientId, StatusEnum status, String templateI);
    boolean existsByToUser_IdAndStatusAndTemplate_Id(String patientId, StatusEnum status, String templateId);*/
    List<Event> findByFromUser_IdAndStatus(String id, StatusEnum status);
    boolean existsByFromUser_IdAndTemplate_IdAndStatus(String userId, String templateId, StatusEnum status);
    boolean existsByToUser_IdAndTemplate_IdAndStatus(String userId, String templateId, StatusEnum status);
}
