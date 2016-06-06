package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.StateDto;

public interface EventNotificationService {
    StateDto sentAction(Event event);
}
