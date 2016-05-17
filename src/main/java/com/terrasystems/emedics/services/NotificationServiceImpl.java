package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.NotificationRepository;
import com.terrasystems.emedics.dao.UserFormRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Notification;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.UserForm;
import com.terrasystems.emedics.model.dto.NotificationDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.NEVER)
public class NotificationServiceImpl implements NotificationService, CurrentUserService {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserFormRepository userFormRepository;

    @Override
    public List<NotificationDto> getReceived() {
        NotificationMapper mapper = new NotificationMapper();
        User current = userRepository.findByEmail(getPrincipals());
        List<NotificationDto> dtos = mapper
                .fromNotifications(notificationRepository.findByFromUser_Id(current.getId()));
        return dtos;
    }

    @Override
    public List<NotificationDto> getSent() {
        NotificationMapper mapper = new NotificationMapper();
        User current = userRepository.findByEmail(getPrincipals());
        List<NotificationDto> dtos = mapper
                .fromNotifications(notificationRepository.findByToUser_Id(current.getId()));
        return dtos;
    }

    @Override
    public NotificationDto getById(String id) {
        return null;
    }

    @Override
    public StateDto sendNotification(NotificationDto notificationToSend) {
        StateDto state = new StateDto();
        NotificationMapper mapper = NotificationMapper.getInstance();
        User current = userRepository.findByEmail(getPrincipals());
        Notification notification;
        UserForm form = userFormRepository.findOne(notificationToSend.getUserForm());
        User user = userRepository.findOne(notificationToSend.getTo());
        notification = mapper.fromDto(notificationToSend);
        notification.setFromUser(current);
        notification.setToUser(user);
        notification.setUserForm(form);
        notificationRepository.save(notification);
        state.setMessage("Notification sent");
        state.setValue(true);
        return state;
    }

    @Override
    public StateDto accept(String id) {
        return null;
    }

    @Override
    public StateDto decline(String id) {
        return null;
    }
}
