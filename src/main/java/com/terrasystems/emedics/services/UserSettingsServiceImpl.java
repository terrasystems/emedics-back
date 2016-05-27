package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.ChangePasswordDto;
import com.terrasystems.emedics.model.dto.RegisterResponseDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;
import com.terrasystems.emedics.model.mapping.UserMapper;
import com.terrasystems.emedics.security.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;

@Service
public class UserSettingsServiceImpl implements UserSettingsService, CurrentUserService {
    private final TokenUtil tokenUtil;

    @Autowired
    public UserSettingsServiceImpl(@Value("${token.secret}") String secret) {
        tokenUtil = new TokenUtil(DatatypeConverter.parseBase64Binary(secret));
    }

    @Autowired
    UserRepository userRepository;


    @Override
    public RegisterResponseDto editUser(UserDto userDto) {
        User user = userRepository.findByEmail(getPrincipals());
        RegisterResponseDto response = new RegisterResponseDto();
        StateDto state = new StateDto();
        UserMapper userMapper = new UserMapper();
        if (user.getEmail().equals(userDto.getEmail())) {
            user.setName(userDto.getUsername());
            user.setTypeExp(userDto.getTypeExp());
            userRepository.save(user);
            state.setValue(true);
            state.setMessage("Updated");
            String token = tokenUtil.createTokenForUser(user);
            String[] type = token.split(":");
            response.setUser(userMapper.toDTO(user));
            response.setToken(token);
            response.setState(state);
            return response;
        } else if (userRepository.existsByEmail(userDto.getEmail())){
            state.setValue(false);
            state.setMessage("User with this email already registered");
            String token = tokenUtil.createTokenForUser(user);
            String[] type = token.split(":");
            response.setUser(userMapper.toDTO(user));
            response.setToken(token);
            response.setState(state);
            return response;
        } else {
            user.setName(userDto.getUsername());
            user.setTypeExp(userDto.getTypeExp());
            user.setEmail(userDto.getEmail());
            userRepository.save(user);
            state.setValue(true);
            state.setMessage("Updated");
            String token = tokenUtil.createTokenForUser(user);
            String[] type = token.split(":");
            response.setUser(userMapper.toDTO(user));
            response.setState(state);
            response.setToken(token);
            return response;
        }
    }

    @Override
    public RegisterResponseDto changePassword(ChangePasswordDto changePasswordDto) {
        User user = userRepository.findByEmail(getPrincipals());
        RegisterResponseDto response = new RegisterResponseDto();
        StateDto state = new StateDto();
        if(user.getPassword().equals(changePasswordDto.getOldPass())) {
            user.setPassword(changePasswordDto.getNewPass());
            userRepository.save(user);
            String token = tokenUtil.createTokenForUser(user);
            String[] type = token.split(":");
            state.setValue(true);
            state.setMessage("Changed");
            response.setState(state);
            response.setToken(token);
            return response;
        } else {
            state.setValue(false);
            state.setMessage("Old password are incorrect");
            String token = tokenUtil.createTokenForUser(user);
            String[] type = token.split(":");
            response.setState(state);
            response.setToken(token);
            return response;
        }
    }


}