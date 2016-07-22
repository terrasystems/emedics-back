package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.NotificationDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/notifications")
public class NotificationControllerV2 {

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllNotifications(@RequestBody CriteriaDto criteria) {
        List<NotificationDto> notificationDtos = new ArrayList<>();
        notificationDtos.add(new NotificationDto("id"));
        notificationDtos.add(new NotificationDto("id"));
        return new ResponseDto(true, "Base msg", notificationDtos);
    }

    @RequestMapping(value = "/accept/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto acceptNotifications(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/decline/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto declineNotifications(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }
}
