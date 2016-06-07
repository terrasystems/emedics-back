package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StateDto;

import java.util.List;

public interface EventNotificationService {
    StateDto sentAction(String eventId, String toUser, String message);
    List<EventDto> getNotifications();
}
