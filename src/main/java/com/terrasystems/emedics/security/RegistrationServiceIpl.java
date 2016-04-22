package com.terrasystems.emedics.security;

import com.terrasystems.emedics.dao.RoleRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.RegisterDto;
import com.terrasystems.emedics.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationServiceIpl implements RegistrationService {
    private static final String TYPE_DOCTOR = "doc";
    private static final String TYPE_PATIENT = "pat";
    private static final String TYPE_ORGANISATION = "org";
    private static final String ROLE_PATIENT = "ROLE_PATIENT";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public String registerUser(UserDto user, String type) {
        String msg;
        switch (type) {
            case TYPE_DOCTOR:
                msg = registerDoctor(user);
                break;
            case TYPE_PATIENT:
                msg = registerPatient(user);
                break;
            case TYPE_ORGANISATION:
                msg = registerOrganisation("mock");
                break;
            default:
                msg = "Bad type";

        }
        return msg;
    }

    public String registerPatient(UserDto user) {
        Patient registerUser = new Patient(user.getUsername(), user.getPassword(), user.getEmail());
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_PATIENT);
        role.setUser(registerUser);
        roles.add(role);
        registerUser.setRoles(roles);
        userRepository.save(registerUser);

        return "Registered";
    }


    public String registerDoctor(UserDto user) {
        Doctor registerUser = new Doctor(user.getUsername(), user.getPassword(), user.getEmail());
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_DOCTOR);
        role.setUser(registerUser);
        roles.add(role);
        registerUser.setRoles(roles);
        userRepository.save(registerUser);

        return "Registered";
    }

    @Override
    public String registerOrganisation(String mock) {
        return mock;
    }

    public UserDto getUserDto(RegisterDto registerDto) {
        return registerDto.getUser();
    }


}
