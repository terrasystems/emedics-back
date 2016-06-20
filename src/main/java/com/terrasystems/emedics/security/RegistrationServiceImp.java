package com.terrasystems.emedics.security;

import com.terrasystems.emedics.dao.OrganizationRepository;
import com.terrasystems.emedics.dao.RoleRepository;
import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.security.token.TokenAuthService;
import com.terrasystems.emedics.security.token.TokenUtil;
import com.terrasystems.emedics.services.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.DatatypeConverter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RegistrationServiceImp implements RegistrationService {
    private static final String TYPE_DOCTOR = "doc";
    private static final String TYPE_PATIENT = "pat";
    private static final String TYPE_ORGANISATION = "org";
    private static final String ROLE_PATIENT = "ROLE_PATIENT";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    private static final String ROLE_STUFF_ADMIN = "ROLE_STUFF_ADMIN";
    private static final String ROLE_STUFF = "ROLE_STUFF";
    private static final String USER_EXIST = MessageEnums.MSG_USER_EXIST.toString();
    public static final String REGISTERED = MessageEnums.MSG_SEND_LETTER.toString();
    private static Map<String,String> emailsStore= new ConcurrentHashMap<>();
    private final TokenUtil tokenUtil;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TokenAuthService tokenService;
    @Autowired
    MailService mailService;

    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    StuffRepository stuffRepository;

    @Autowired
    public RegistrationServiceImp(@Value("${token.secret}") String secret) {
        tokenUtil = new TokenUtil(DatatypeConverter.parseBase64Binary(secret));
    }


    @Override
    //@Transactional(propagation = Propagation.REQUIRED)
    public StateDto registerUser(RegisterDto registerDto, String type) {

        System.out.println("enter registerUser method");
        StateDto stateDto = new StateDto();
        StateDto mailState;
        UserDto user = getUserDto(registerDto);
        OrganisationDto org = getOrgDto(registerDto);
        if (user.getPassword() == null) {
            user.setPassword(RandomStringUtils.random(10, 'a','b','c','A','B','C','1','2','3','4','5'));
        }

        if (userRepository.existsByEmail(user.getEmail())){
            stateDto.setMessage(USER_EXIST);
            stateDto.setValue(false);
            return stateDto;
        } else {
            String activateToken = RandomStringUtils.random(10, 'a', 'b', 'c','d','e','f');
            System.out.println(user.getEmail());
            System.out.println(activateToken);
            emailsStore.put(activateToken, user.getEmail());

            mailState = mailService.sendRegistrationMail(user.getEmail(), activateToken, user.getPassword());
            System.out.println("mailState.msg = "+ mailState.getMessage());
        if (mailState.isValue()) {
            System.out.println(type);
            switch (type) {
                case TYPE_DOCTOR:
                    stateDto = registerDoctor(user);
                    break;
                case TYPE_PATIENT:
                    stateDto = registerPatient(user);
                    break;
                case TYPE_ORGANISATION:
                    stateDto = registerOrganisation(user,org);
                    break;
                default: {
                    stateDto.setMessage(MessageEnums.MSG_REG_FAILED.toString());
                    stateDto.setValue(false);
                    break;
                }

        }}
            else return mailState;
        }
        //if (stateDto.isValue()) {userFormsDashboardService.generateFormsForUser(user.getEmail()); return stateDto;}

        return stateDto;
    }

    public StateDto registerPatient(UserDto user) {
        StateDto stateDto = new StateDto();
        Patient registerUser = new Patient();
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_PATIENT);
        role.setUser(registerUser);
        roles.add(role);
        registerUser.setRoles(roles);
        registerUser.setDoctors(new ArrayList<>());
        registerUser.setUsers(new HashSet<>());
        registerUser.setUserRef(new HashSet<>());
        registerUser.setFirstName(user.getFirstName());
        registerUser.setLastName(user.getLastName());
        registerUser.setBirth(user.getBirth());
        registerUser.setEmail(user.getEmail());
        registerUser.setPassword(user.getPassword());
        registerUser.setPhone(user.getPhone());
        userRepository.save(registerUser);
        stateDto.setMessage(REGISTERED);
        stateDto.setValue(true);
        return stateDto;
    }
    @Override
    //@Transactional(propagation = Propagation.MANDATORY)
    public StateDto registerDoctor(UserDto user) {

        System.out.println("entering registerDoctor method");
        StateDto stateDto = new StateDto();
        Doctor registerUser = new Doctor();
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_DOCTOR);
        role.setUser(registerUser);
        roles.add(role);
        registerUser.setRoles(roles);
        registerUser.setPatients(new ArrayList<>());
        registerUser.setStuff(new ArrayList<>());
        registerUser.setUsers(new HashSet<>());
        registerUser.setUserRef(new HashSet<>());
        registerUser.setFirstName(user.getFirstName());
        registerUser.setLastName(user.getLastName());
        registerUser.setBirth(user.getBirth());
        registerUser.setEmail(user.getEmail());
        registerUser.setPassword(user.getPassword());
        registerUser.setPhone(user.getPhone());
        userRepository.save(registerUser);
        stateDto.setMessage(REGISTERED);
        stateDto.setValue(true);
        System.out.println("returning from registerDoctor method");
        return stateDto;
    }

    @Override
    public StateDto registerOrganisation(UserDto user, OrganisationDto org) {
        StateDto status = new StateDto();
        Doctor registeredUser = new Doctor();
        registeredUser.setOrg(true);
        registeredUser.setOrgName(org.getName());
        registeredUser.setOrgAddress(org.getAddress());
        registeredUser.setOrgName(org.getFullname());
        registeredUser.setOrgWebSite(org.getWebsite());
        registeredUser.setOrgType(org.getType());
        registeredUser.setOrgDescription(org.getDescr());
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_STUFF_ADMIN);
        Role role2 = new Role(ROLE_DOCTOR);
        role.setUser(registeredUser);
        role2.setUser(registeredUser);
        roles.add(role);
        roles.add(role2);
        registeredUser.setStuff(new ArrayList<>());
        registeredUser.setRoles(roles);
        registeredUser.setPatients(new ArrayList<>());
        registeredUser.setStuff(new ArrayList<>());
        registeredUser.setUsers(new HashSet<>());
        registeredUser.setUserRef(new HashSet<>());
        registeredUser.setFirstName(user.getFirstName());
        registeredUser.setLastName(user.getLastName());
        registeredUser.setBirth(user.getBirth());
        registeredUser.setEmail(user.getEmail());
        registeredUser.setPassword(user.getPassword());
        registeredUser.setPhone(user.getPhone());

        userRepository.save(registeredUser);

        status.setMessage(MessageEnums.MSG_USER_REG.toString());
        status.setValue(true);
        return status;
    }

    @Override
    public StateDto resetPassword(String email) {
        User loadedUser = userRepository.findByEmail(email);
        StateDto state = new StateDto();
        if (loadedUser == null) {

            state.setMessage(MessageEnums.MSG_EMAIL_NOT_EXIST.toString());
            state.setValue(false);
            return state;
        }
        String valueKey = RandomStringUtils.randomAlphabetic(10);
        state = mailService.sendResetPasswordMail(email, valueKey);
        if (state.isValue()){
            loadedUser.setValueKey(valueKey);
            userRepository.save(loadedUser);
            return state;
        }
        return state;
    }

    @Override
    public StateDto validationKey(String key) {
        User current = userRepository.findByValueKey(key);
        StateDto state = new StateDto();
        if(current == null) {
            state.setMessage(MessageEnums.MSG_EMAIL_NOT_EXIST.toString());
            state.setValue(false);
            return state;
        } else {
            state.setMessage("Key is valid");
            state.setValue(true);
            return state;
        }
    }

    @Override
    public StateDto changePassword(String key, String newPassword) {
        User current = userRepository.findByValueKey(key);
        StateDto state = new StateDto();
        if (current == null) {
            state.setMessage(MessageEnums.MSG_EMAIL_NOT_EXIST.toString());
            state.setValue(false);
            return state;
        } else {
            current.setPassword(newPassword);
            current.setValueKey(null);
            userRepository.save(current);
            state.setMessage("Password changed");
            state.setValue(true);
            return state;
        }
    }

    @Override
    public RegisterResponseDto activateUser(String link) {
        String email = emailsStore.get(link);
        if (email == null) {
            User user = userRepository.findByActivationToken(link);
            if (user == null) {
                return new RegisterResponseDto(null, null, new StateDto(false, MessageEnums.MSG_BAD.toString()));
            } else {
                user.setEnabled(true);
                String token = tokenUtil.createTokenForUser(user);
                user.setRegistrationDate(new Date());
                user.setEnabled(true);
                userRepository.save(user);
                //userFormsDashboardService.generateFormsForUser(email);
                RegisterResponseDto response = new RegisterResponseDto();
                StateDto state = new StateDto(true, MessageEnums.MSG_USER_ACTIVED.toString());
                UserDto userDto = new UserDto(user.getEmail(), user.getUsername());
                String[] type = token.split(":");
                userDto.setType(type[2]);
                response.setState(state);
                response.setToken(token);
                response.setUser(userDto);
                //TODO delete link after activation
                return response;
            }
        }
        User user = userRepository.findByEmail(emailsStore.get(link));
        String token = tokenUtil.createTokenForUser(user);
        user.setRegistrationDate(new Date());
        user.setEnabled(true);
        userRepository.save(user);
        //userFormsDashboardService.generateFormsForUser(email);
        RegisterResponseDto response = new RegisterResponseDto();
        StateDto state = new StateDto(true, MessageEnums.MSG_USER_ACTIVED.toString());
        UserDto userDto = new UserDto(user.getEmail(), user.getUsername());
        String[] type = token.split(":");
        userDto.setType(type[2]);
        response.setState(state);
        response.setToken(token);
        response.setUser(userDto);
        //TODO delete link after activation
        return response;
    }

    public UserDto getUserDto(RegisterDto registerDto) {
        return registerDto.getUser();
    }

    public OrganisationDto getOrgDto(RegisterDto registerDto) {
        return registerDto.getOrganisation();
    }

}
