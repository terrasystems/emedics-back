package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dto.NotificationDto;
import com.terrasystems.emedics.model.dto.StateDto;

import java.util.List;

public interface NotificationService  {
    List<NotificationDto> getReceived();
    List<NotificationDto> getSent();
    NotificationDto getById(String id);
    StateDto sendNotification(NotificationDto notificationToSend);
    StateDto accept(String id);
    StateDto decline(String id);
}
