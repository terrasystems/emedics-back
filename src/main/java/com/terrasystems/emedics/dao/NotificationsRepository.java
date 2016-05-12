package com.terrasystems.emedics.dao;

import com.terrasystems.emedics.model.Notifications;
import com.terrasystems.emedics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface NotificationsRepository extends CrudRepository<Notifications, String> {
}
