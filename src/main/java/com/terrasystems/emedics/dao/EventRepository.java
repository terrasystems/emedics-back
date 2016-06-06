package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, String> {
    List<Event> findByStatusAndTo_user_id(String status, String id);
}
