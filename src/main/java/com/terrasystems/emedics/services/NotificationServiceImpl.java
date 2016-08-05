package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.NotificationDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.mapping.NotificationMapper;
import com.terrasystems.emedics.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    Utils utils;
    @Autowired
    EventRepository eventRepository;

    @Override
    public ResponseDto allNotifications(CriteriaDto dto) {
        if (dto.getSearch() == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        User user = utils.getCurrentUser();
        List<NotificationDto> notifications = eventRepository.findByToUser_IdAndStatusAndTemplate_NameContainingIgnoreCaseOrToUser_IdAndStatusAndFromUser_NameContainingIgnoreCase(user.getId(), StatusEnum.SENT, dto.getSearch().toLowerCase(), user.getId(), StatusEnum.SENT, dto.getSearch().toLowerCase()).stream()
                .map(notification -> {
                    NotificationMapper mapper = NotificationMapper.getInstance();
                    return mapper.toDto(notification);
                }).collect(Collectors.toList());
        return utils.generateResponse(true, MessageEnums.MSG_ALL_NOTIFICATIONS.toString(), notifications);
    }

    @Override
    public ResponseDto acceptNotification(String id) {
        Event event = eventRepository.findOne(id);
        if(event == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        User user = utils.getCurrentUser();
        if (utils.isPatient(user)) {
            return acceptNotificationForPatient(user, event);
        }
        return acceptNotificationForPatientNonPatient(user, event);
    }

    @Override
    public ResponseDto declineNotification(String id) {
        Event event = eventRepository.findOne(id);
        if(event == null) return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        event.setStatus(StatusEnum.DECLINED);
        eventRepository.save(event);
        return utils.generateResponse(true, MessageEnums.MSG_NOTIFICATION_DECLINED.toString(), null);
    }

    private ResponseDto acceptNotificationForPatient(User user, Event event) {
        return checkAlreadyHaveThisTask(user, event);
    }

    private ResponseDto acceptNotificationForPatientNonPatient(User user, Event event) {
        if (utils.isMedicalForm(event.getTemplate())) {
            return checkAlreadyHaveThisTask(user, event);
        } else {
            return acceptNotificationLogic(user, event);
        }
    }

    private ResponseDto checkAlreadyHaveThisTask(User user, Event event) {
        Long countNew = eventRepository.countByFromUserAndTemplateAndStatus(user,event.getTemplate(),StatusEnum.NEW);
        Long countNewByFrom = eventRepository.countByFromUserAndTemplateAndStatus(user,event.getTemplate(),StatusEnum.PROCESSED);
        Long countProcessed = eventRepository.countByToUserAndTemplateAndStatus(user,event.getTemplate(),StatusEnum.PROCESSED);
        if (countNew > 0 || countProcessed > 0 || countNewByFrom > 0) return utils.generateResponse(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null);
        return acceptNotificationLogic(user, event);
    }

    private ResponseDto acceptNotificationLogic(User user, Event event) {
        Event newEvent = cloneEvent(event);
        newEvent.setId(null);
        event.setStatus(StatusEnum.CLOSED);
        eventRepository.save(event);
        eventRepository.save(newEvent);
        return utils.generateResponse(true, MessageEnums.MSG_NOTIFICATION_ACCEPTED.toString(), null);
    }

    private static Event cloneEvent(Event event) {
        Event newEvent = new Event();
        newEvent.setStatus(StatusEnum.PROCESSED);
        newEvent.setData(event.getData());
        newEvent.setFromUser(event.getToUser());
        newEvent.setDescr(event.getDescr());
        newEvent.setDate(new Date());
        newEvent.setPatient(event.getPatient());
        newEvent.setTemplate(event.getTemplate());
        newEvent.setToUser(null);
        return newEvent;
    }
}
