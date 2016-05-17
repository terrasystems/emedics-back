package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.DashboardNotificationResponse;
import com.terrasystems.emedics.model.dto.DashboardNotificationsRequest;
import com.terrasystems.emedics.model.dto.NotificationDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/private/dashboard")
public class NotificationController {

    @Autowired
    NotificationService notificationsService;

/*

    @RequestMapping(value = "/notifications/edit", method = RequestMethod.POST)
    @ResponseBody
    public DashboardNotificationResponse notificationEdit(@RequestBody NotificationsDto request) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        Notification notifications = notificationsService.editNotifications(request);
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
*/



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
/*

@RequestMapping(value = "/notifications/add", method = RequestMethod.POST)
    @ResponseBody
    public DashboardNotificationResponse notificationsAdd(@RequestBody NotificationsDto request) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
    }

    @RequestMapping(value = "/notifications/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardNotificationResponse notificationsRemove(@PathVariable String id) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        if(notificationsService.getNotificationsById(id) == null) {
            response.setState(new StateDto(false, "Notification doesn't exist"));
        } else {
            notificationsService.removeNotifications(id);
            response.setState(new StateDto(true, "Notification remove"));
        }

        return response;
    }
*/

/*


    @RequestMapping(value = "notifications/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DashboardNotificationResponse notificationsGetById(@PathVariable String id) {
        DashboardNotificationResponse response = new DashboardNotificationResponse();
        Notification notifications = notificationsService.getNotificationsById(id);

        if(notifications != null) {
            NotificationsDto  notificationsDto = new NotificationsDto(notifications.getId(), notifications.getTimestamp(), notifications.getReadtype(),
                    notifications.getType(), notifications.getTitle(), notifications.getText());
            response.setState(new StateDto(true, "Notification by id"));
            response.setResult(notificationsDto);
        } else {
            response.setState(new StateDto(false, "Notification with such id doesn't exist"));
            response.setResult(null);
        }


        return response;
    }


*/


}
