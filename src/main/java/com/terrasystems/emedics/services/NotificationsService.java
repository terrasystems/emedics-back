package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.Notifications;
import com.terrasystems.emedics.model.dto.NotificationsDto;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public interface NotificationsService extends CurrentUserService {
    List<Notifications> getAllNotifications();
    Notifications editNotifications(NotificationsDto notificationsDto);
    void removeNotifications(String id);
    Notifications getNotificationsById(String id);
    default String getPrincipals() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
