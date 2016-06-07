package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.services.EventNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/private/dashboard/tasks")
public class EventNotificationController {

    @Autowired
    EventNotificationService eventNotificationService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public DashboardEventResponse sendNotification(@RequestBody EventSendRequest request){
        DashboardEventResponse response = new DashboardEventResponse();
        response.setState(eventNotificationService.sentAction(request.getEvent(),request.getToUser(),request.getMessage()));
        return response;
    }

    @RequestMapping(value = "/notifications/all", method = RequestMethod.GET)
    @ResponseBody
    public DashboardEventResponse getAllNotifications(){
        DashboardEventResponse response = new DashboardEventResponse();
        response.setState(new StateDto(true, "Notifications"));
        response.setResult(eventNotificationService.getNotifications());
        return response;
    }
}
