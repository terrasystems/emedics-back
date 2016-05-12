package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.NotificationsRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Notifications;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.NotificationsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.NEVER)
public class NotificationServiceImpl implements NotificationsService, CurrentUserService {

    @Autowired
    NotificationsRepository repository;

    @Autowired
    UserRepository userRepository;

    @Override

    public List<Notifications> getAllNotifications() {
        List<Notifications> notifications = (List<Notifications>) repository.findAll();
        return notifications;
    }

    public void addNotifications(Notifications notifications) {
        this.repository.save(notifications);
    }

/*    @Override

    public Notifications editNotifications(NotificationsDto notificationsDto) {

        if(notificationsDto.getId() == null) {
            Notifications notifications = new Notifications();
            notifications.setId(notificationsDto.getId());
            notifications.setReadtype(notificationsDto.getReadtype());
            notifications.setText(notificationsDto.getText());
            notifications.setTimestamp(notificationsDto.getTimestamp());
            notifications.setTitle(notificationsDto.getTitle());
            repository.save(notifications);
            return notifications;
        } else {
            Notifications notifications = new Notifications();
            notifications.setId(notificationsDto.getId());
            notifications.setType(notificationsDto.getType());
            notifications.setReadtype(notificationsDto.getReadtype());
            notifications.setText(notificationsDto.getText());
            notifications.setTimestamp(notificationsDto.getTimestamp());
            notifications.setTitle(notificationsDto.getTitle());
            repository.save(notifications);
            return notifications;
        }
    }*/

/*
    @Override

    public void removeNotifications(String id) {
        repository.delete(id);
    }

    @Override

    public Notifications getNotificationsById(String id) {
        Notifications notifications = repository.findOne(id);
        return notifications;
    }
*/




}
