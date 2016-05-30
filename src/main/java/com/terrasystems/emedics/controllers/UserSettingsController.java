package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.services.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/private")
public class UserSettingsController {

    @Autowired
    UserSettingsService userSettingsService;

    @RequestMapping(value = "/user_edit", method = RequestMethod.POST)
    @ResponseBody
    public RegisterResponseDto editUser(@RequestBody UserDto userDto) {
        return userSettingsService.editUser(userDto);
    }

    @RequestMapping(value = "/change_pass",method = RequestMethod.POST)
    @ResponseBody
    public RegisterResponseDto changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return userSettingsService.changePassword(changePasswordDto);
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    @ResponseBody
    public RegisterResponseDto settingsPage() {
        return userSettingsService.settingsPage();
    }

}
