package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.services.EventNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/private/dashboard/")
public class EventNotificationController {

    @Autowired
    EventNotificationService eventNotificationService;

    @RequestMapping(value = "tasks/send", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse sendNotification(@RequestBody EventSendRequest request){
        DashboardEventResponse response = new DashboardEventResponse();
        response.setState(eventNotificationService.sentAction(request.getEvent(),request.getToUser(),request.getMessage(),request.getPatient()));
        return response;
    }

    @RequestMapping(value = "events/notifications/all", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse getAllNotifications(@RequestBody NotificationCriteria criteria){
        DashboardEventResponse response = new DashboardEventResponse();
        response.setState(new StateDto(true, "Notifications"));
        response.setResult(eventNotificationService.getNotifications(criteria));
        return response;
    }

    @RequestMapping(value = "events/notifications/accept/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardEventResponse acceptNotification(@PathVariable String id){
        DashboardEventResponse response = new DashboardEventResponse();
        response.setState(eventNotificationService.acceptNotification(id));
        response.setResult(null);
        return response;
    }

    @RequestMapping(value = "events/notifications/decline/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardEventResponse declineNotification(@PathVariable String id){
        DashboardEventResponse response = new DashboardEventResponse();
        response.setState(eventNotificationService.declineNotification(id));
        response.setResult(null);
        return response;
    }



}
