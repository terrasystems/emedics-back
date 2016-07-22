package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.model.dtoV2.ChangePasswordDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v2/user")
public class UserControllerV2 {

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto getUser(@PathVariable String id) {
        return new ResponseDto(true, "Base msg", new UserDto());
    }

    @RequestMapping(value = "/change_pass", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getAllUsers(@RequestBody ChangePasswordDto request) {
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(new UserDto("id"));
        userDtos.add(new UserDto("id"));
        return new ResponseDto(true, "Base msg", userDtos);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto getMyUsers(@RequestBody UserDto request) {
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(new UserDto("id"));
        userDtos.add(new UserDto("id"));
        return new ResponseDto(true, "Base msg", userDtos);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto createUser(@PathVariable String id) {
        return new ResponseDto(true, "Base msg");
    }


}
