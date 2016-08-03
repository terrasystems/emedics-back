package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.ChangePasswordDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.UserDto;
import com.terrasystems.emedics.services.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/user")
public class UserControllerV2 {

    @Autowired
    UserSettingsService userSettingsService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getUser(@PathVariable String id) {
        return userSettingsService.getUserById(id);
    }

    @RequestMapping(value = "/change_pass", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto changePassword(@RequestBody ChangePasswordDto request) {
        return userSettingsService.changePassword(request);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto editUser(@RequestBody UserDto request) {
        return userSettingsService.editUser(request);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto removeUser(@PathVariable String id) {
        return userSettingsService.removeUser(id);
    }


}
