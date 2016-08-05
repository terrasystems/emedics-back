package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.NotificationDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/notifications")
public class NotificationControllerV2 {

    @Autowired
    NotificationService notificationService;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllNotifications(@RequestBody CriteriaDto criteria) {
        return notificationService.allNotifications(criteria);
    }

    @RequestMapping(value = "/accept/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto acceptNotifications(@PathVariable String id) {
        return notificationService.acceptNotification(id);
    }

    @RequestMapping(value = "/decline/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto declineNotifications(@PathVariable String id) {
        return notificationService.declineNotification(id);
    }
}
