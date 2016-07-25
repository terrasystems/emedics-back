package com.terrasystems.emedics.controllersV2;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.*;
import com.terrasystems.emedics.model.mapping.UserMapper;
import com.terrasystems.emedics.security.JWT.JwtTokenUtil;
import com.terrasystems.emedics.security.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v2/auth")
public class PublicControllerV2 {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("emedics")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil generateToken;

    @Autowired
    private UserRepository userRepository;



    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto registration(@RequestBody UserDto request) {
        ResponseDto responseDto = registrationService.registerUser(request);
        return responseDto;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto login(@RequestBody LoginDto loginDto) throws AuthenticationException {
        ResponseDto responseDto = new ResponseDto();
        UserMapper userMapper = UserMapper.getInstance();

        /*final UserDetails userDetails =  userDetailsService.loadUserByUsername(loginDto.getEmail());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        userDetails.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);*/
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user != null && user.getPassword().equals(loginDto.getPassword())) {
            final String token = generateToken.generateToken(user);
            UserDto dto = userMapper.toDTO(user);
            dto.setToken(token);
            responseDto.setState(true);
            responseDto.setMsg("Auth ok");
            responseDto.setResult(dto);

            return responseDto;
        } else {
            responseDto.setState(false);
            responseDto.setMsg("User with such email doesn't exist or password is incorrect");
            return responseDto;
        }
    }

    @RequestMapping(value = "/activate/{key}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto activate(@PathVariable String key) {
        ResponseDto responseDto = registrationService.activateUser(key);
        return responseDto;
    }

    @RequestMapping(value = "/reset_pass", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto resetPass(@RequestBody UserDto userDto) {
        if (userDto.getEmail() == null) {
            return new ResponseDto(false, "email is null");
        }
        ResponseDto response = registrationService.resetPassword(userDto);
        response.setResult(userDto.getEmail());
        return response;
    }

    @RequestMapping(value = "/change_pass", method = RequestMethod.POST)
    @ResponseBody
    public ResponseDto changePassword(@RequestBody ResetPasswordDto request) {
        ResponseDto responseDto = registrationService.changePassword(request);
        return responseDto;
    }

    @RequestMapping(value = "/check_email/{email}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto checkEmail(@PathVariable String email) {
        ResponseDto responseDto = registrationService.checkEmail(email);
        return responseDto;
    }

    @RequestMapping(value = "/check_key/{key}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseDto checkKey(@PathVariable String key) {
        return registrationService.checkKey(key);
    }


}
