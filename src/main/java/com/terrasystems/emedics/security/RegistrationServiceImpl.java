package com.terrasystems.emedics.security;


import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.dtoV2.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private static Map<String,String> emailsStore= new ConcurrentHashMap<>();

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseDto registerUser(UserDto userDto) {

        ResponseDto responseDto = new ResponseDto();

        if (userDto.getPass() == null) {
            userDto.setPass(RandomStringUtils.random(10, 'a','b','c','A','B','C','1','2','3','4','5'));
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            responseDto.setState(false);
            responseDto.setMsg("User with such email exist");
            return responseDto;
        } else {
            String activateToken = RandomStringUtils.random(10, 'a', 'b', 'c','d','e','f');
            emailsStore.put(activateToken, userDto.getEmail());
        }

        return null;
    }

    @Override
    public UserDto loginUser(LoginDto loginDto) {
        return null;
    }

    @Override
    public ResponseDto activateUser(String key) {
        return null;
    }

    @Override
    public ResponseDto resetPassword(ResetPasswordDto resetPasswordDto) {
        return null;
    }

    @Override
    public UserDto changePassword(ChangePasswordDto changePasswordDto) {
        return null;
    }

    @Override
    public ResponseDto checkEmail(String email) {
        return null;
    }

    @Override
    public ResponseDto checkKey(String key) {
        return null;
    }
}
