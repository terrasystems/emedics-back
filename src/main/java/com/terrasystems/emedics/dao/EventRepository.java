package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, String> {
    List<Event> findByToUser_IdAndStatus(String id, StatusEnum status);
}
