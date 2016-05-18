package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/private/dashboard")
public class NotificationController {

    @Autowired
    NotificationService notificationsService;



    @RequestMapping(value = "/notifications", method = RequestMethod.POST)
    @ResponseBody
    public DashboardNotificationResponse notificationsGetAll(@RequestBody DashboardNotificationsRequest request) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        StateDto state = new StateDto(true,"Your notifications");
        List<NotificationDto> notifications = notificationsService.getReceived();
        if (notifications == null) {
            state.setValue(false);
            state.setMessage("Can't get notifications");
        }

        response.setResult(notifications);
        response.setState(state);
        return response;
    }
    @RequestMapping(value = "/notifications/send", method = RequestMethod.POST)
    @ResponseBody
    public DashboardNotificationResponse notificationsSend(@RequestBody DashboardNotificationsRequest request) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        StateDto status;
        status = notificationsService.sendNotification(request.getNotification());
        response.setState(status);
        return response;
    }
    @RequestMapping(value = "/notifications/accept/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardNotificationResponse notificationAccept(@PathVariable String id) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        response.setState(notificationsService.accept(id));
        return response;
    }
}
