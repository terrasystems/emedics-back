package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.security.RegistrationService;
import com.terrasystems.emedics.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/rest/public")
public class PublicController {


    @Autowired
    RegistrationService registrationService;
    @Autowired
    MailService mailService;


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public RegisterResponseDto registerUser(@RequestBody RegisterDto registerDto) {

        System.out.println("Begin registration method");
        StateDto status;
        String type = registerDto.getType();
        UserDto user = registerDto.getUser();
        status = registrationService.registerUser(registerDto,type);


        RegisterResponseDto response = new RegisterResponseDto();
        response.setState(status);
        user.setPassword(null);
        response.setUser(user);
        System.out.println("Sending response" );
        return response;
    }



    @RequestMapping(value = "reset_pass", method = RequestMethod.POST)
    @ResponseBody
    public RegisterResponseDto resetPass(@RequestBody String email) {
        RegisterResponseDto response = new RegisterResponseDto();
        StateDto state = registrationService.resetPassword(email);
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/activate/{token}", method = RequestMethod.GET)
    @ResponseBody
    public RegisterResponseDto activateUser(@PathVariable String token) {
        return registrationService.activateUser(token);

    }




}


