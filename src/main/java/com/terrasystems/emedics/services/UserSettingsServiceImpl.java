package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.DocTypeRepository;
import com.terrasystems.emedics.dao.DoctorRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.Doctor;
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
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSettingsServiceImpl implements UserSettingsService, CurrentUserService {
    private final TokenUtil tokenUtil;

    @Autowired
    public UserSettingsServiceImpl(@Value("${token.secret}") String secret) {
        tokenUtil = new TokenUtil(DatatypeConverter.parseBase64Binary(secret));
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    DocTypeRepository docTypeRepository;


    @Override
    @Transactional
    public RegisterResponseDto editUser(UserDto userDto) {
        User user = userRepository.findByEmail(getPrincipals());
        RegisterResponseDto response = new RegisterResponseDto();
        StateDto state = new StateDto();
        UserMapper userMapper = new UserMapper();
        UserDto respDto;
        if (user.getEmail().equals(userDto.getEmail())) {
            if (user.getDiscriminatorValue().equals("doctor")) {
                ((Doctor) user).setType(docTypeRepository.findOne(userDto.getDoctorType()));
            }
            user.setTypeExp(userDto.getTypeExp());
            user.setBirth(userDto.getBirth());
            user.setLastName(userDto.getLastName());
            user.setFirstName(userDto.getFirstName());
            if ((userDto.getFirstName() == null) && (userDto.getLastName() == null)) {
                user.setName(userDto.getEmail());
            } else {
                user.setName(userDto.getFirstName() == null ? userDto.getLastName() : userDto.getFirstName());
            }
            userRepository.save(user);
            state.setValue(true);
            state.setMessage(MessageEnums.MSG_UPDATE.toString());
            String token = tokenUtil.createTokenForUser(user);
            String[] type = token.split(":");
            respDto = userMapper.toDTO(user);
            respDto.setType(type[2]);
            response.setUser(respDto);
            response.setToken(token);
            response.setState(state);
            return response;
        } else if (userRepository.existsByEmail(userDto.getEmail())){
            state.setValue(false);
            state.setMessage(MessageEnums.MSG_EMAIL_EXIST.toString());
            String token = tokenUtil.createTokenForUser(user);
            String[] type = token.split(":");
            respDto = userMapper.toDTO(user);
            respDto.setType(type[2]);
            response.setUser(respDto);
            response.setToken(token);
            response.setState(state);
            return response;
        } else {
            user.setTypeExp(userDto.getTypeExp());
            user.setBirth(userDto.getBirth());
            user.setLastName(userDto.getLastName());
            user.setFirstName(userDto.getFirstName());
            user.setEmail(userDto.getEmail());
            if ((userDto.getFirstName() == null) && (userDto.getLastName() == null)) {
                user.setName(userDto.getEmail());
            } else {
                user.setName(userDto.getFirstName() == null ? userDto.getLastName() : userDto.getFirstName());
            }
            userRepository.save(user);
            if(user.getDiscriminatorValue().equals("doctor")) {
                Doctor doctor = doctorRepository.findOne(user.getId());

                //doctor.getType().setName(userDto.getDoctorType()); <- This is dich
                doctor.setType(docTypeRepository.findOne(userDto.getDoctorType()));
                doctor.setOrgType(userDto.getOrgType());
                doctorRepository.save(doctor);
            }
            state.setValue(true);
            state.setMessage(MessageEnums.MSG_UPDATE.toString());
            String token = tokenUtil.createTokenForUser(user);
            String[] type = token.split(":");
            respDto = userMapper.toDTO(user);
            respDto.setType(type[2]);
            response.setUser(respDto);
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
            state.setMessage(MessageEnums.MSG_CHANGE.toString());
            response.setState(state);
            response.setToken(token);
            return response;
        } else {
            state.setValue(false);
            state.setMessage(MessageEnums.MSG_OLD_PASS_BAD.toString());
            String token = tokenUtil.createTokenForUser(user);
            String[] type = token.split(":");
            response.setState(state);
            response.setToken(token);
            return response;
        }
    }

    @Override
    public RegisterResponseDto settingsPage() {
        User user = userRepository.findByEmail(getPrincipals());
        RegisterResponseDto response = new RegisterResponseDto();
        UserMapper userMapper = new UserMapper();
        UserDto userDto = userMapper.toDTO(user);
        if(user.getDiscriminatorValue().equals("doctor")){
            Doctor doctor = doctorRepository.findOne(user.getId());
            userDto.setDoctorType(doctor.getType().getId());
            userDto.setOrgType(doctor.getOrgType());

        }

        response.setUser(userDto);
        response.setState(new StateDto(true, "Settings Page"));
        return response;
    }

    @Override
    public StateDto checkEmail(String email) {
        StateDto state = new StateDto();
        List<User> users = (List<User>) userRepository.findAll();
        List<String> usersEmail = users.stream()
                .map(user -> user.getEmail())
                .collect(Collectors.toList());
        if(usersEmail.contains(email)) {
            state.setMessage("This email address exist already");
            state.setValue(false);
            return state;
        }
        state.setMessage("Email correct");
        state.setValue(true);
        return state;
    }


}