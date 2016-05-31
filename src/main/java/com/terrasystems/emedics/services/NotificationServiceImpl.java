package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.*;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.NotificationDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.mapping.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Autowired
    SharedFormRepository sharedFormRepository;
    @Autowired
    HistoryRepository historyRepository;

    @Override
    public List<NotificationDto> getReceived() {
        NotificationMapper mapper = new NotificationMapper();
        User current = userRepository.findByEmail(getPrincipals());
        List<NotificationDto> dtos = mapper
                .fromNotifications(notificationRepository.findByToUser_IdAndReadtypeNull(current.getId()));
        return dtos;
    }

    @Override
    public List<NotificationDto> getSent() {
        NotificationMapper mapper = new NotificationMapper();
        User current = userRepository.findByEmail(getPrincipals());
        List<NotificationDto> dtos = mapper
                .fromNotifications(notificationRepository.findByFromUser_Id(current.getId()));
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
        User user = userRepository.findOne(notificationToSend.getToUser().getId());
        notification = mapper.fromDto(notificationToSend);
        notification.setFromUser(current);
        notification.setToUser(user);
        notification.setUserForm(form);
        notificationRepository.save(notification);
        //form.setData("{}");
        form.setActive(false);
        userFormRepository.save(form);
        state.setMessage("MSG_NOTIF_SEND");
        state.setValue(true);
        return state;
    }

    @Override
    public StateDto accept(String id) {
        StateDto state = new StateDto();
        User current = userRepository.findByEmail(getPrincipals());
        Notification notification = notificationRepository.findOne(id);
        if (current.getDiscriminatorValue().equals("patient")) {
            UserForm form = notification.getUserForm();
            UserForm userForm = userFormRepository.findByUser_IdAndBlank_Id(current.getId(),form.getBlank().getId());
            if (!userForm.isActive()) {
                userForm.setActive(true);
            }
            userForm.setData(form.getData());
            userFormRepository.save(userForm);
            state.setMessage("MSG_NOTIF_ACCEPT");
            state.setValue(true);
            return state;
        }

        UserForm userForm = notification.getUserForm();
        SharedForm sharedForm = sharedFormRepository.findByBlank_IdAndPatient_Id(userForm.getBlank().getId(), userForm.getUser().getId());
        if (sharedForm == null) {
            sharedForm = new SharedForm();
            sharedForm.setUser(current);
            sharedForm.setBlank(userForm.getBlank());
            sharedForm.setPatient((Patient) userRepository.findByEmail(notification.getFromUser().getEmail()));
            sharedForm.setData(userForm.getData());
        } else {
            sharedForm.setData(userForm.getData());
        }
        sharedFormRepository.save(sharedForm);
        notification.setReadtype(true);
        notificationRepository.save(notification);
        History history = new History();
        history.setData(notification.getUserForm().getData());
        history.setDate(new Date());
        history.setUserForm(notification.getUserForm());
        historyRepository.save(history);
        state.setMessage("MSG_NOTIF_ACCEPT");
        state.setValue(true);
        return state;
    }

    @Override
    public StateDto decline(String id) {
        StateDto state = new StateDto();
        Notification notification = notificationRepository.findOne(id);
        notification.setReadtype(false);
        notificationRepository.save(notification);
        state.setMessage("MSG_NOTIF_DECLINE");
        state.setValue(true);

        return state;
    }
}
