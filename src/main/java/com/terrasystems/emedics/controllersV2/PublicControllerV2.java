package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.*;
import com.terrasystems.emedics.security.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v2/auth")
public class PublicControllerV2 {

    @Autowired
    RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto registration(@RequestBody UserDto request) {
        ResponseDto responseDto = registrationService.registerUser(request);
        return responseDto;
    }

    @RequestMapping(value = "/activate/{key}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto activate(@PathVariable String key) {
        ResponseDto responseDto = registrationService.activateUser(key);
        return responseDto;
    }

    @RequestMapping(value = "/reset_pass", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto resetPassword(@RequestBody ResetPasswordDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/change_pass", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto changePassword(@RequestBody ChangePasswordDto request) {
        return new ResponseDto(true, "Base msg", new UserDto());
    }

    @RequestMapping(value = "/check_email/{email}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto checkEmail(@PathVariable String email) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/check_key/{key}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto checkKey(@PathVariable String key) {
        return new ResponseDto(true, "Base msg");
    }


}
