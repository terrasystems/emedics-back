package com.terrasystems.emedics.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.terrasystems.emedics.dao.RoleRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.RegisterDto;
import com.terrasystems.emedics.model.dto.UserDto;
import com.terrasystems.emedics.security.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/rest/public")
public class PublicController {


    @Autowired
    RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public String registerUser(@RequestBody RegisterDto registerDto) {
        String msg = null;
        String type = registerDto.getType();
        switch (type) {
            case "org":
                msg = registrationService.registerOrganisation("mock");
                break;
            default:
               msg = registrationService.registerUser(getUserDto(registerDto), type);
        }


        return String.format("{\"message\":\"%s\"}", msg);
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


