package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.DashboardTemplateResponse;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.EventNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/private")
public class EventNotificationController {

    @Autowired
    EventNotificationService eventNotificationService;

    @RequestMapping(value = "/tasks/send/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardTemplateResponse sendNotification(@PathVariable String id){
        DashboardTemplateResponse response = new DashboardTemplateResponse();
        response.setState(eventNotificationService.sentAction(id));
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
