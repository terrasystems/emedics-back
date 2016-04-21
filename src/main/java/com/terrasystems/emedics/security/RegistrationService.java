package com.terrasystems.emedics.security;

import com.terrasystems.emedics.dao.RoleRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public String registerDoctor(Doctor user) {
        /*User savedUser = user;
        savedUser.setClinic("test");
        Set<Role> userRoles = new HashSet<>();
        for (String role : roles){
            userRoles.add(new Role(role).setUser(savedUser));
        }*/


        return null;
    }
}
