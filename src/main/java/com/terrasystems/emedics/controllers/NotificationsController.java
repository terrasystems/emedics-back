package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.Notifications;
import com.terrasystems.emedics.model.dto.DashboardNotificationResponse;
import com.terrasystems.emedics.model.dto.DashboardNotificationsRequest;
import com.terrasystems.emedics.model.dto.NotificationsDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/private/dashboard")
public class NotificationsController {

    @Autowired
    NotificationsService notificationsService;

    @RequestMapping(value = "/notifications/edit", method = RequestMethod.POST)
    @ResponseBody
    public DashboardNotificationResponse notificationEdit(@RequestBody NotificationsDto request) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        Notifications notifications = notificationsService.editNotifications(request);
        List<NotificationsDto> notificationsDto = new ArrayList<>();
        StateDto state = new StateDto();
        if(notifications == null) {
            state.setMessage("error");
            state.setValue(false);
        } else {
            state.setMessage("Edited");
            state.setValue(true);
            notificationsDto.add(new NotificationsDto(notifications.getId(), notifications.getTimestamp(), notifications.getReadtype(),
                    notifications.getType(), notifications.getTitle(), notifications.getText()));
        }

        response.setState(state);
        response.setResult(notificationsDto);
        return response;
    }

    @RequestMapping(value = "/notifications", method = RequestMethod.POST)
    @ResponseBody
    public DashboardNotificationResponse notificationsGetAll(@RequestBody DashboardNotificationsRequest request) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        List<NotificationsDto> notifications = notificationsService.getAllNotifications().stream()
                .map(item -> {
                    NotificationsDto notificationsDto = new NotificationsDto();
                    notificationsDto.setTitle(item.getTitle());
                    notificationsDto.setType(item.getType());
                    notificationsDto.setTimestamp(item.getTimestamp());
                    notificationsDto.setReadtype(item.getReadtype());
                    notificationsDto.setText(item.getText());
                    return notificationsDto;
                }).collect(Collectors.toList());
        response.setResult(notifications);
        response.setState(new StateDto(true, "All notifications"));
        return response;
    }

    @RequestMapping(value = "/notifications/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardNotificationResponse notificationsRemove(@PathVariable String id) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        if(notificationsService.getNotificationsById(id) == null) {
            response.setState(new StateDto(false, "Notifications doesn't exist"));
        } else {
            notificationsService.removeNotifications(id);
            response.setState(new StateDto(true, "Notifications remove"));
        }

        return response;
    }

    @RequestMapping(value = "notifications/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardNotificationResponse notificationsGetById(@PathVariable String id) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        Notifications notifications = notificationsService.getNotificationsById(id);

        if(notifications != null) {
            NotificationsDto  notificationsDto = new NotificationsDto(notifications.getId(), notifications.getTimestamp(), notifications.getReadtype(),
                    notifications.getType(), notifications.getTitle(), notifications.getText());
            response.setState(new StateDto(true, "Notifications by id"));
            response.setResult(notificationsDto);
        } else {
            response.setState(new StateDto(false, "Notifications with such id doesn't exist"));
            response.setResult(null);
        }


        return response;
    }


}
