package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.EventDto;
import com.terrasystems.emedics.model.dto.StateDto;

import java.util.List;

public interface EventNotificationService {
    StateDto sentAction(String eventId, String toUser, String message, String patientId);
    StateDto declineNotification(String eventId);
    StateDto acceptNotification(String eventId);
    List<EventDto> getNotifications();
}
