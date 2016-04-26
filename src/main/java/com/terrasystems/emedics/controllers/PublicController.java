package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.RegisterDto;
import com.terrasystems.emedics.model.dto.RegisterResponseDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;
import com.terrasystems.emedics.security.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/public")
public class PublicController {


    @Autowired
    RegistrationService registrationService;


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public RegisterResponseDto registerUser(@RequestBody RegisterDto registerDto) {
        StateDto status = null;
        String type = registerDto.getType();
        UserDto user = getUserDto(registerDto);
        switch (type) {
            case "org":
                status = registrationService.registerOrganisation("mock");
                break;
            default:
               status =   registrationService.registerUser(user, type);
        }

        RegisterResponseDto response = new RegisterResponseDto();
        response.setState(status);
        user.setPassword(null);
        response.setUser(user);
        return response;
    }

    @RequestMapping(value = "reset_pass", method = RequestMethod.POST)
    @ResponseBody
    public String resetPass(@RequestBody String email) {

        return String.format("{\"newPassword\":\"%s\"}",registrationService.resetPassword(email));
    }

    public UserDto getUserDto(RegisterDto registerDto) {
        return registerDto.getUser();
    }


}


