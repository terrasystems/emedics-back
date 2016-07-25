package com.terrasystems.emedics.security;


import com.terrasystems.emedics.dao.OrganizationRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Organization;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.*;
import com.terrasystems.emedics.model.mapping.UserMapper;
import com.terrasystems.emedics.security.JWT.JwtTokenUtil;
import com.terrasystems.emedics.services.CurrentUserService;
import com.terrasystems.emedics.services.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistrationServiceImpl implements RegistrationService, CurrentUserService {
    private static Map<String,String> emailsStore= new ConcurrentHashMap<>();
    private final JwtTokenUtil tokenUtil;

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    MailService mailService;
    @Autowired
    public RegistrationServiceImpl(@Value("${token.secret}") String secret) {
        tokenUtil = new JwtTokenUtil();
    }

    @Override
    @Transactional
    public ResponseDto registerUser(UserDto userDto) {

        ResponseDto responseDto = new ResponseDto();
        ResponseDto mailState;

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
            mailState = mailService.sendRegistrationMail(userDto.getEmail(), activateToken, userDto.getPass());
            if (mailState.getState()) {
                User registerUser = new User();
                Set<Role> roles = new HashSet<>();
                Role role = new Role("ROLE_"+userDto.getUserType().toString());
                role.setUser(registerUser);
                roles.add(role);
                registerUser.setRoles(roles);
                registerUser.setUsers(new HashSet<>());
                registerUser.setReferences(new HashSet<>());
                registerUser.setFirstName(userDto.getFirstName());
                registerUser.setLastName(userDto.getLastName());
                registerUser.setBirth(userDto.getDob());
                registerUser.setEmail(userDto.getEmail());
                registerUser.setPassword(userDto.getPass());
                registerUser.setPhone(userDto.getPhone());
                registerUser.setUserType(userDto.getUserType());
                registerUser.setActivationToken(activateToken);
                if (userDto.getAdmin()) {
                    registerUser.setAdmin(true);
                    Organization organization = new Organization();
                    registerUser.setOrganization(organization);
                    organization.setAddress(userDto.getAddress());
                    organization.setName(userDto.getOrgName());
                    organization.setWebsite(userDto.getWebsite());
                    organization.setDescr(userDto.getDescr());
                    organizationRepository.save(organization);
                }
                userRepository.save(registerUser);
                responseDto.setMsg(MessageEnums.MSG_SEND_LETTER.toString());
                responseDto.setState(true);
                return responseDto;
            } else {
                return mailState;
            }
        }
    }

    @Override
    @Transactional
    public ResponseDto activateUser(String key) {
        UserMapper mapper = UserMapper.getInstance();
        String email = emailsStore.get(key);
        if (email == null) {
            User user = userRepository.findByActivationToken(key);
            if (user == null) {
                return new ResponseDto(false, MessageEnums.MSG_BAD.toString());
            } else {
                user.setEnabled(true);
                user.setRegistrationDate(new Date());
                user.setEnabled(true);
                user.setActivationToken(null);
                userRepository.save(user);
                ResponseDto response = new ResponseDto(true, MessageEnums.MSG_USER_ACTIVED.toString());
                return response;
            }
        }
        User user = userRepository.findByEmail(emailsStore.get(key));
        user.setRegistrationDate(new Date());
        user.setEnabled(true);
        user.setActivationToken(null);
        ResponseDto response = new ResponseDto(true, MessageEnums.MSG_USER_ACTIVED.toString());

        userRepository.save(user);
        return response;
    }

    @Override
    public ResponseDto loginUser(LoginDto loginDto) {
        ResponseDto responseDto = new ResponseDto();
        User user = userRepository.findByEmail(loginDto.getEmail());
        UserMapper userMapper = UserMapper.getInstance();
        if (user == null) {
            responseDto.setState(false);
            responseDto.setMsg("Password or email are incorrect");
            return responseDto;
        }

        if (loginDto.getPassword().equals(user.getPassword())) {
            UserDto dto = userMapper.toDTO(user);
            dto.setToken(user.getEmail() + ":" + user.getPassword() + ":" + user.getUserType().toString());
            responseDto.setMsg("Login correct");
            responseDto.setState(true);
            responseDto.setResult(dto);
            return responseDto;
        } else {
            responseDto.setState(false);
            responseDto.setMsg("Password or email are incorrect");
            return responseDto;
        }
    }

    @Override
    public ResponseDto resetPassword(UserDto userDto) {
        User loadedUser = userRepository.findByEmail(userDto.getEmail());
        ResponseDto responseDto = new ResponseDto();
        if (loadedUser == null) {
            responseDto.setMsg(MessageEnums.MSG_EMAIL_NOT_EXIST.toString());
            responseDto.setState(false);
            return responseDto;
        }
        String valueKey = RandomStringUtils.randomAlphabetic(10);
        responseDto = mailService.sendResetPasswordMail(userDto.getEmail(), valueKey);
        if (responseDto.getState()){
            loadedUser.setValueKey(valueKey);
            userRepository.save(loadedUser);
            return responseDto;
        }
        return responseDto;
    }

    @Override
    public ResponseDto changePassword(ResetPasswordDto resetPasswordDto) {
        User current = userRepository.findByValueKey(resetPasswordDto.getValidKey());
        ResponseDto responseDto = new ResponseDto();
        if (current == null) {
            responseDto.setMsg(MessageEnums.MSG_EMAIL_NOT_EXIST.toString());
            responseDto.setState(false);
            return responseDto;
        } else {
            current.setPassword(resetPasswordDto.getNewPassword());
            current.setValueKey(null);
            userRepository.save(current);
            responseDto.setMsg("Password changed");
            responseDto.setState(true);
            return responseDto;
        }
    }

    @Override
    public ResponseDto checkEmail(String email) {
        boolean emailExist = userRepository.existsByEmail(email);
        ResponseDto responseDto = new ResponseDto();
        if(emailExist) {
            responseDto.setMsg("User with this email exist");
            responseDto.setState(false);
            return responseDto;
        } else {
            responseDto.setMsg("Email is valid");
            responseDto.setState(true);
            return responseDto;
        }
    }

    @Override
    public ResponseDto checkKey(String key) {
        User current = userRepository.findByValueKey(key);
        ResponseDto responseDto = new ResponseDto();
        if(current == null) {
            responseDto.setMsg(MessageEnums.MSG_EMAIL_NOT_EXIST.toString());
            responseDto.setState(false);
            return responseDto;
        } else {
            responseDto.setMsg("Key is valid");
            responseDto.setState(true);
            return responseDto;
        }
    }
}
