package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Notifications;
import com.terrasystems.emedics.model.dto.NotificationsDto;

import java.util.List;

public interface NotificationsService {
    List<Notifications> getAllNotifications();
    Notifications editNotifications(NotificationsDto notificationsDto);
    void removeNotifications(String id);
    Notifications getNotificationsById(String id);
}
