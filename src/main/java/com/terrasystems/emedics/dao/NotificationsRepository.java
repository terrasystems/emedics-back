package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.model.Notifications;
import org.springframework.data.repository.CrudRepository;


public interface NotificationsRepository extends CrudRepository<Notifications, String> {
}
