package com.terrasystems.emedics.controllers;

import com.terrasystems.emedics.model.dto.RegisterDto;
import com.terrasystems.emedics.model.dto.RegisterResponseDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;
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
        System.out.println("Sending responce" );
        return response;
    }

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RegisterResponseDto login() {
        r
    }*/

    @RequestMapping(value = "reset_pass", method = RequestMethod.POST)
    @ResponseBody
    public StateDto resetPass(@RequestBody String email) {

        return registrationService.resetPassword(email);
    }

    @RequestMapping(value = "/activate/{token}", method = RequestMethod.GET)
    @ResponseBody
    public RegisterResponseDto activateUser(@PathVariable String token) {
        return registrationService.activateUser(token);

    }

    public UserDto getUserDto(RegisterDto registerDto) {
        return registerDto.getUser();
    }


}


