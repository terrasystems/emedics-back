package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, String> {

}
