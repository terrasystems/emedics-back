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
    public DashboardEventResponse sendNotification(@RequestBody DashboardEventRequest request){
        DashboardEventResponse response = new DashboardEventResponse();
        response.setState(eventNotificationService.sentAction(request.getCriteria().getEvent(),request.getCriteria().getToUser()));
        return response;
    }

    @RequestMapping(value = "/tasks/notifications/list", method = RequestMethod.GET)
    @ResponseBody
    public DashboardTemplateResponse getAllNotifications(){
        DashboardTemplateResponse response = new DashboardTemplateResponse();
        response.setState(new StateDto(true, "Notifications"));
        response.setResult(eventNotificationService.getNotifications());
        return response;
    }
}
