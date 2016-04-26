package com.terrasystems.emedics.security;

import com.terrasystems.emedics.dao.RoleRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.RegisterDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;
import com.terrasystems.emedics.security.token.TokenAuthService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationServiceImp implements RegistrationService {
    private static final String TYPE_DOCTOR = "doc";
    private static final String TYPE_PATIENT = "pat";
    private static final String TYPE_ORGANISATION = "org";
    private static final String ROLE_PATIENT = "ROLE_PATIENT";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    private static final String USER_EXIST = "User with such password is already exist";
    public static final String REGISTERED = "Registered";
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TokenAuthService tokenService;

    @Override
    public StateDto registerUser(UserDto user, String type) {
        StateDto stateDto = null;


        switch (type) {
            case TYPE_DOCTOR:
                stateDto = registerDoctor(user);
                break;
            case TYPE_PATIENT:
                stateDto = registerPatient(user);
                break;
            case TYPE_ORGANISATION:
                stateDto = registerOrganisation("mock");
                break;
            default:
                stateDto.setMessage("Registration failed");
                stateDto.setValue(false);
        }

        return stateDto;
    }

    public StateDto registerPatient(UserDto user) {
        StateDto stateDto = new StateDto();
        if (userRepository.existsByEmail(user.getEmail())){
            stateDto.setMessage(USER_EXIST);
            stateDto.setValue(false);
        } else {
            Patient registerUser = new Patient(user.getUsername(), user.getPassword(), user.getEmail());
            Set<Role> roles = new HashSet<>();
            Role role = new Role(ROLE_PATIENT);
            role.setUser(registerUser);
            roles.add(role);
            registerUser.setRoles(roles);
            userRepository.save(registerUser);

            stateDto.setMessage(REGISTERED);
            stateDto.setValue(true);
        }
        return stateDto;
    }


    public StateDto registerDoctor(UserDto user) {
        StateDto stateDto = new StateDto();
        if (userRepository.existsByEmail(user.getEmail())){
            stateDto.setMessage(USER_EXIST);
            stateDto.setValue(false);
        }
        else {


            Doctor registerUser = new Doctor(user.getUsername(), user.getPassword(), user.getEmail());
            Set<Role> roles = new HashSet<>();
            Role role = new Role(ROLE_DOCTOR);
            role.setUser(registerUser);
            roles.add(role);
            registerUser.setRoles(roles);
            userRepository.save(registerUser);

            stateDto.setMessage(REGISTERED);
            stateDto.setValue(true);
        }
        return stateDto;
    }

    @Override
    public StateDto registerOrganisation(String mock) {
        StateDto status = new StateDto();
        status.setMessage("not supported yet");
        status.setValue(false);
        return status;
    }

    @Override
    public String resetPassword(String email) {
        User loadedUser = userRepository.findByEmail(email);
        String newpass = RandomStringUtils.randomAscii(7);
        loadedUser.resetPassword(newpass);
        userRepository.save(loadedUser);
        return newpass;
    }

    public UserDto getUserDto(RegisterDto registerDto) {
        return registerDto.getUser();
    }


}
