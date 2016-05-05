package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.NotificationsRepository;
import com.terrasystems.emedics.model.Notifications;
import com.terrasystems.emedics.model.dto.NotificationsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.NEVER)
public class NotificationServiceImpl implements NotificationsService {

    @Autowired
    NotificationsRepository repository;

    @Override
    @Transactional
    public List<Notifications> getAllNotifications() {
        List<Notifications> notifications = (List<Notifications>) repository.findAll();
        return notifications;
    }

    @Override
    @Transactional
    public Notifications editNotifications(NotificationsDto notificationsDto) {

        if(notificationsDto.getId() == null) {
            Notifications notifications = new Notifications();
            notifications.setReadtype(notificationsDto.getReadtype());
            notifications.setText(notificationsDto.getText());
            notifications.setTimestamp(notificationsDto.getTimestamp());
            notifications.setTitle(notificationsDto.getTitle());
            repository.save(notifications);
            return notifications;
        } else {
            Notifications notifications = new Notifications();
            notifications.setType(notificationsDto.getType());
            notifications.setReadtype(notificationsDto.getReadtype());
            notifications.setText(notificationsDto.getText());
            notifications.setTimestamp(notificationsDto.getTimestamp());
            notifications.setTitle(notificationsDto.getTitle());
            repository.save(notifications);
            return notifications;
        }
    }

    @Override
    @Transactional
    public void removeNotifications(String id) {
        repository.delete(id);
    }

    @Override
    @Transactional
    public Notifications getNotificationsById(String id) {
        Notifications notifications = repository.findOne(id);
        return notifications;
    }




}
