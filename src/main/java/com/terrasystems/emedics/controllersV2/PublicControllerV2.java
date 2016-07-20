package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dto.ChangePasswordDto;
import com.terrasystems.emedics.model.dtoV2.LoginDto;
import com.terrasystems.emedics.model.dtoV2.ResetPasswordDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.UserDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v2/auth")
public class PublicControllerV2 {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto login(@RequestBody LoginDto request) {
        return new ResponseDto(true, "Base msg", new UserDto());
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto registration(@RequestBody UserDto request) {
        return new ResponseDto(true, "Base msg");
    }

    @RequestMapping(value = "/activate/{key}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto activate(@PathVariable String key) {
        return new ResponseDto(true, "Base msg");
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
