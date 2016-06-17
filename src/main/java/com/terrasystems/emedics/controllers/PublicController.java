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
        if (!status.isValue()) {
            response.setUser(null);
        } else {
            user.setPassword(null);
            response.setUser(user);
        }

        System.out.println("Sending response" );
        return response;
    }



    @RequestMapping(value = "/reset_pass", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResponse resetPass(@RequestBody String email) {
        ObjectResponse response = new ObjectResponse();
        StateDto state = registrationService.resetPassword(email);
        response.setState(state);
        response.setResult(email);
        return response;
    }

    @RequestMapping(value = "/validation_key/{validKey}", method = RequestMethod.GET)
    @ResponseBody
    public ObjectResponse validationKey(@PathVariable String validKey) {
        ObjectResponse response = new ObjectResponse();
        StateDto state = registrationService.validationKey(validKey);
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public ObjectResponse changePassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        ObjectResponse response = new ObjectResponse();
        StateDto state = registrationService.changePassword(resetPasswordDto.getValidKey(), resetPasswordDto.getNewPassword());
        response.setState(state);
        return response;
    }

    @RequestMapping(value = "/activate/{token}", method = RequestMethod.GET)
    @ResponseBody
    public RegisterResponseDto activateUser(@PathVariable String token) {
        return registrationService.activateUser(token);

    }




}


