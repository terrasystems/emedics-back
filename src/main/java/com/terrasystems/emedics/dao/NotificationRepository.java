package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface NotificationRepository extends CrudRepository<Notification, String> {
    List<Notification> findByFromUser_Id(String id);
    List<Notification> findByToUser_Id(String id);
}
