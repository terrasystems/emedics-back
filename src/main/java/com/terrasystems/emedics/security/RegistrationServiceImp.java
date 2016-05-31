package com.terrasystems.emedics.security;

import com.terrasystems.emedics.dao.OrganizationRepository;
import com.terrasystems.emedics.dao.RoleRepository;
import com.terrasystems.emedics.dao.StuffRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.*;
import com.terrasystems.emedics.security.token.TokenAuthService;
import com.terrasystems.emedics.security.token.TokenUtil;
import com.terrasystems.emedics.services.MailService;
import com.terrasystems.emedics.services.UserFormsDashboardService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private static final String ROLE_STUFF = "ROLE_STUFF_ADMIN";
    private static final String USER_EXIST = "User with such email address is already exist";
    public static final String REGISTERED = "We send the letter with activation cod on your email address. Please activate your account.";
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
    UserFormsDashboardService userFormsDashboardService;
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    StuffRepository stuffRepository;

    @Autowired
    public RegistrationServiceImp(@Value("${token.secret}") String secret) {
        tokenUtil = new TokenUtil(DatatypeConverter.parseBase64Binary(secret));
    }


    @Override
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
                default:
                    stateDto.setMessage("Registration failed");
                    stateDto.setValue(false);

        }}
            else return mailState;
        }
        //if (stateDto.isValue()) {userFormsDashboardService.generateFormsForUser(user.getEmail()); return stateDto;}

        return stateDto;
    }

    public StateDto registerPatient(UserDto user) {
        StateDto stateDto = new StateDto();
        Patient registerUser = new Patient(user.getUsername(), user.getPassword(), user.getEmail());
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_PATIENT);
        role.setUser(registerUser);
        roles.add(role);
        registerUser.setRoles(roles);
        userRepository.save(registerUser);
        stateDto.setMessage(REGISTERED);
        stateDto.setValue(true);
        return stateDto;
    }


    public StateDto registerDoctor(UserDto user) {

        System.out.println("entering registerDoctor method");
        StateDto stateDto = new StateDto();
        Doctor registerUser = new Doctor(user.getUsername(), user.getPassword(), user.getEmail());
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_DOCTOR);
        role.setUser(registerUser);
        roles.add(role);
        registerUser.setRoles(roles);
        userRepository.save(registerUser);
        stateDto.setMessage(REGISTERED);
        stateDto.setValue(true);
        System.out.println("returning from registerDoctor method");
        return stateDto;
    }

    @Override
    public StateDto registerOrganisation(UserDto user, OrganisationDto org) {
        StateDto status = new StateDto();
        Stuff registeredUser = new Stuff(user.getUsername(), user.getPassword(), user.getEmail());
        registeredUser.setAdmin(true);
        Organization organization = new Organization();
        organization.setName(org.getName());
        organization.setAddress(org.getAddress());
        organization.setFullName(org.getFullname());
        organization.setWebsite(org.getWebsite());
        organization.setType(org.getType());
        organization.setDescr(org.getDescr());
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_STUFF);
        role.setUser(registeredUser);
        roles.add(role);

        List<Stuff> stuffs = new ArrayList<>();
        stuffs.add(registeredUser);
        organization.setStuff(stuffs);
        registeredUser.setOrganization(organization);
        registeredUser.setRoles(roles);
        organizationRepository.save(organization);

        status.setMessage("MSG_USER_REG");
        status.setValue(true);
        return status;
    }

    @Override
    public StateDto resetPassword(String email) {
        User loadedUser = userRepository.findByEmail(email);
        StateDto state = new StateDto();
        if (loadedUser == null) {

            state.setMessage("MSG_EMAIL_NOT_EXIST");
            state.setValue(false);
            return state;
        }
        String newpass = RandomStringUtils.randomAscii(7);
        state = mailService.sendResetPasswordMail(email, newpass);
        if (state.isValue()){
            loadedUser.resetPassword(newpass);
            userRepository.save(loadedUser);
            return state;
        }
        return state;
    }

    @Override
    public RegisterResponseDto activateUser(String link) {
        String email = emailsStore.get(link);
        if (email == null) {
            return new RegisterResponseDto(null, null, new StateDto(false, "Bad activating code"));
        }
        User user = userRepository.findByEmail(emailsStore.get(link));
        String token = tokenUtil.createTokenForUser(user);
        user.setRegistrationDate(new Date());
        user.setEnabled(true);
        user.setEnabled(true);
        userRepository.save(user);
        userFormsDashboardService.generateFormsForUser(email);
        RegisterResponseDto response = new RegisterResponseDto();
        StateDto state = new StateDto(true, "MSG_USER_ACTIVED");
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
