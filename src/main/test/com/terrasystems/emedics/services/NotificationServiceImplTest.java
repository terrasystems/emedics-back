package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.EventRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.NotificationDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

    @InjectMocks
    NotificationServiceImpl notificationServiceImpl;

    @Mock
    Utils utils;
    @Mock
    EventRepository eventRepository;

    @Test
    public void test_will_return_incorrect_request_for_allNotifications() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        CriteriaDto criteriaDto = new CriteriaDto();

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = notificationServiceImpl.allNotifications(criteriaDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_all_notifications() {
        User user = new User();
        CriteriaDto criteriaDto = new CriteriaDto();
        criteriaDto.setSearch("");
        List<NotificationDto> notifications = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_ALL_NOTIFICATIONS.toString(), notifications);
        List<Event> events = new ArrayList<>();

        when(utils.generateResponse(true, MessageEnums.MSG_ALL_NOTIFICATIONS.toString(), notifications)).thenReturn(responseDto);
        when(utils.getCurrentUser()).thenReturn(user);
        when(eventRepository.findByToUser_IdAndStatusAndTemplate_NameContainingIgnoreCaseOrToUser_IdAndStatusAndFromUser_NameContainingIgnoreCase(user.getId(), StatusEnum.SENT, criteriaDto.getSearch().toLowerCase(), user.getId(), StatusEnum.SENT, criteriaDto.getSearch().toLowerCase())).thenReturn(events);

        ResponseDto responseDto1 = notificationServiceImpl.allNotifications(criteriaDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_declineNotification() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = notificationServiceImpl.declineNotification("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_decline_notification() {
        Event event = new Event();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_NOTIFICATION_DECLINED.toString(), null);

        when(utils.generateResponse(true, MessageEnums.MSG_NOTIFICATION_DECLINED.toString(), null)).thenReturn(responseDto);
        when(eventRepository.findOne("")).thenReturn(event);

        ResponseDto responseDto1 = notificationServiceImpl.declineNotification("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_acceptNotification() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = notificationServiceImpl.acceptNotification("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_you_already_have_this_task() {
        Event event = new Event();
        User user = new User();
        Template template = new Template();
        event.setTemplate(template);
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(eventRepository.findOne("")).thenReturn(event);
        when(utils.isPatient(user)).thenReturn(true);
        when(eventRepository.countByFromUserAndTemplateAndStatus(user,event.getTemplate(),StatusEnum.NEW)).thenReturn(1l);
        when(eventRepository.countByToUserAndTemplateAndStatus(user,event.getTemplate(),StatusEnum.PROCESSED)).thenReturn(1l);
        when(utils.generateResponse(false, MessageEnums.MSG_YOU_ALREADY_HAVE_THIS_TASK.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = notificationServiceImpl.acceptNotification("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_accept_notification_with_medical_form() {
        Event event = new Event();
        User user = new User();
        Template template = new Template();
        event.setTemplate(template);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_NOTIFICATION_ACCEPTED.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(eventRepository.findOne("")).thenReturn(event);
        when(utils.isPatient(user)).thenReturn(false);
        when(utils.isMedicalForm(event.getTemplate())).thenReturn(true);
        when(eventRepository.countByFromUserAndTemplateAndStatus(user,event.getTemplate(),StatusEnum.NEW)).thenReturn(0l);
        when(eventRepository.countByToUserAndTemplateAndStatus(user,event.getTemplate(),StatusEnum.PROCESSED)).thenReturn(0l);
        when(utils.generateResponse(true, MessageEnums.MSG_NOTIFICATION_ACCEPTED.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = notificationServiceImpl.acceptNotification("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_accept_notification_with_non_medical_form_to_non_patient() {
        Event event = new Event();
        User user = new User();
        Template template = new Template();
        event.setTemplate(template);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_NOTIFICATION_ACCEPTED.toString(), null);

        when(utils.getCurrentUser()).thenReturn(user);
        when(eventRepository.findOne("")).thenReturn(event);
        when(utils.isPatient(user)).thenReturn(false);
        when(utils.isMedicalForm(event.getTemplate())).thenReturn(false);
        when(utils.generateResponse(true, MessageEnums.MSG_NOTIFICATION_ACCEPTED.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = notificationServiceImpl.acceptNotification("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

}
