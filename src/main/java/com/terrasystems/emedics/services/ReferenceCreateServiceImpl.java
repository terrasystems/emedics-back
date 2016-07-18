package com.terrasystems.emedics.services;


import com.terrasystems.emedics.dao.DocTypeRepository;
import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.ReferenceCreateRequest;
import com.terrasystems.emedics.model.dto.StateDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class ReferenceCreateServiceImpl implements ReferenceCreateService {
    private static final String TYPE_DOCTOR = "doc";
    private static final String TYPE_PATIENT = "pat";
    private static final String TYPE_ORGANISATION = "org";
    private static final String ROLE_PATIENT = "ROLE_PATIENT";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    private static final String ROLE_STUFF = "ROLE_STUFF_ADMIN";

    @Autowired
    UserRepository userRepository;
    @Autowired
    DocTypeRepository docTypeRepository;
    @Autowired
    MailService mailService;



    @Override
    @Transactional
    public Patient createPatient(ReferenceCreateRequest request) {
        Patient registerUser = new Patient();
        registerUser.setPassword(RandomStringUtils.random(10, 'a','b','c','A','B','C','1','2','3','4','5'));
        registerUser.setEmail(request.getEmail());
        registerUser.setFirstName(request.getFirstName());
        registerUser.setLastName(request.getLastName());
        registerUser.setBirth(request.getBirth());
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_PATIENT);
        role.setUser(registerUser);
        roles.add(role);
        registerUser.setRoles(roles);
        registerUser.setDoctors(new ArrayList<>());
        registerUser.setUserRef(new HashSet<>());
        registerUser.setUsers(new HashSet<>());
        registerUser.setActivationToken(RandomStringUtils.random(10, 'a', 'b', 'c','d','e','f'));
        registerUser.setUserType(UserType.PATIENT);
        userRepository.save(registerUser);
        return registerUser;
    }

    @Override
    @Transactional
    public Doctor createDoctor(ReferenceCreateRequest request) {
        Doctor registerUser = new Doctor();
        registerUser.setPassword(RandomStringUtils.random(10, 'a','b','c','A','B','C','1','2','3','4','5'));
        registerUser.setEmail(request.getEmail());
        registerUser.setFirstName(request.getFirstName());
        registerUser.setLastName(request.getLastName());
        registerUser.setBirth(request.getBirth());
        registerUser.setType(docTypeRepository.findOne(request.getDocType()));
        Set<Role> roles = new HashSet<>();
        Role role = new Role(ROLE_DOCTOR);
        role.setUser(registerUser);
        roles.add(role);
        registerUser.setRoles(roles);
        registerUser.setPatients(new ArrayList<>());
        registerUser.setUsers(new HashSet<>());
        registerUser.setUserRef(new HashSet<>());
        registerUser.setActivationToken(RandomStringUtils.random(10, 'a', 'b', 'c','d','e','f'));
        registerUser.setUserType(UserType.DOCTOR);
        userRepository.save(registerUser);

        return registerUser;
    }

    @Override
    public boolean inviteUser(String id) {
        User user = userRepository.findOne(id);
        StateDto result = mailService.sendRegistrationMail(user.getEmail(),user.getActivationToken(),user.getPassword());

        return result.isValue();
    }

}
