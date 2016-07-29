package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Role;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ReferenceDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import com.terrasystems.emedics.model.mapping.TypeMapper;
import com.terrasystems.emedics.utils.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReferencesServiceImpl implements ReferencesService {

    @Autowired
    Utils utils;
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public ResponseDto getAllReferences(CriteriaDto criteriaDto) {
        User currentUser = utils.getCurrentUser();
        List<ReferenceDto> refs = new ArrayList<>();
        ReferenceConverter converter = new ReferenceConverter();
        if (isPatient(currentUser)) {
            refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch(), criteriaDto.getSearch())));
            return utils.generateResponse(true, MessageEnums.MSG_PAT_REF.toString(), refs);
        } else if (isDoctor(currentUser)) {
            refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch(), criteriaDto.getSearch())));
            refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase(currentUser.getId(), UserType.PATIENT, criteriaDto.getSearch(), criteriaDto.getSearch())));
            return utils.generateResponse(true, MessageEnums.MSG_DOC_REF.toString(), refs);
        } else {
            return null;
        }

    }

    @Override
    @Transactional
    public ResponseDto removeReference(String id) {
        User user = utils.getCurrentUser();
        User refToRemove = userRepository.findById(id);
        if (user == null || refToRemove == null) {
            return utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);
        }
        user.getReferences().remove(refToRemove);
        refToRemove.getReferences().remove(user);
        userRepository.save(user);
        userRepository.save(refToRemove);
        return utils.generateResponse(true, MessageEnums.MSG_REF_REMOVED.toString(), null);
    }

    @Override
    @Transactional
    public ResponseDto addReference(String id) {
        User user = utils.getCurrentUser();
        User refToAdd = userRepository.findById(id);
        if (user == null || refToAdd == null) {
            return utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);
        }
        user.getReferences().add(refToAdd);
        refToAdd.getReferences().add(user);
        userRepository.save(refToAdd);
        userRepository.save(user);
        return utils.generateResponse(true, MessageEnums.MSG_REF_ADDED.toString(), null);
    }

    @Override
    public ResponseDto createReference(ReferenceDto referenceDto) {
        User current = utils.getCurrentUser();
        if (userRepository.existsByEmail(referenceDto.getEmail())) {
            return utils.generateResponse(false, MessageEnums.MSG_EMAIL_EXIST.toString(), null);
        }

        if (isPatient(current)) {
            return createReferenceLogicCurrentPatient(current, referenceDto);
        } else if (isDoctor(current)) {
            return createReferenceLogicCurrentDoctor(current, referenceDto);
        } else return utils.generateResponse(false, MessageEnums.MSG_NOT_SUPPORTED.toString(), null);
    }

    private ResponseDto createReferenceLogicCurrentPatient(User current, ReferenceDto referenceDto) {
        User userToRef = createDoctor(referenceDto);
        if (userToRef == null) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUIRED_FIELDS_EXCEPTION.toString(), null);
        }
        return addToReference(current, userToRef);
    }

    private ResponseDto createReferenceLogicCurrentDoctor(User current, ReferenceDto referenceDto) {
        if (UserType.PATIENT.equals(referenceDto.getUserType())) {
            User userToRef = createPatient(referenceDto);
            if (userToRef == null) {
                return utils.generateResponse(false, MessageEnums.MSG_REQUIRED_FIELDS_EXCEPTION.toString(), null);
            }
            return addToReference(current, userToRef);
        } else {
            User userToRef = createDoctor(referenceDto);
            if (userToRef == null) {
                return utils.generateResponse(false, MessageEnums.MSG_REQUIRED_FIELDS_EXCEPTION.toString(), null);
            }
            return addToReference(current, userToRef);
        }
    }

    private User createDoctor(ReferenceDto referenceDto) {

        String activateToken = createActivationMark(referenceDto);
        User userDoctor = createUser(referenceDto, activateToken);
        Role role = createRole(referenceDto, userDoctor);
        TypeMapper mapper = TypeMapper.getInstance();
        bindUserRole(userDoctor, role);
        if (referenceDto.getType() != null) {
            userDoctor.setType(mapper.toEntity(referenceDto.getType()));
        }
        userRepository.save(userDoctor);
        return userDoctor;
    }

    private User createPatient(ReferenceDto referenceDto) {
        String activateToken = createActivationMark(referenceDto);
        User userPatient = createUser(referenceDto, activateToken);
        Role role = createRole(referenceDto, userPatient);
        bindUserRole(userPatient, role);
        userRepository.save(userPatient);

        return userPatient;
    }

    private ResponseDto addToReference(User current, User userToRef) {
        current.getReferences().add(userToRef);
        userToRef.getReferences().add(current);
        return utils.generateResponse(true, MessageEnums.MSG_REF_CREATED.toString(), null);
    }

    private Role createRole(ReferenceDto referenceDto, User registerUser) {
        Role role = new Role("ROLE_" + referenceDto.getUserType().toString());
        role.setUser(registerUser);
        return role;
    }

    private String createActivationMark(ReferenceDto referenceDto) {
        String activateToken = RandomStringUtils.random(10, 'a', 'b', 'c', 'd', 'e', 'f');
        return activateToken;
    }

    private void bindUserRole(User registerUser, Role role) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        registerUser.setRoles(roles);
    }

    private User createUser(ReferenceDto referenceDto, String activateToken) {
        User createUser = new User();
        createUser.setUserType(referenceDto.getUserType());
        createUser.setPassword(RandomStringUtils.random(10, 'a','b','c','A','B','C','1','2','3','4','5'));
        createUser.setEmail(referenceDto.getEmail());
        createUser.setLastName(referenceDto.getLastName());
        createUser.setFirstName(referenceDto.getFirstName());
        createUser.setBirth(referenceDto.getDob());
        createUser.setUsers(new HashSet<>());
        createUser.setReferences(new HashSet<>());
        createUser.setActivationToken(activateToken);
        return createUser;
    }

    private boolean isPatient(User current) {
        return UserType.PATIENT.equals(current.getUserType());
    }

    private boolean isDoctor(User current) {
        return UserType.DOCTOR.equals(current.getUserType());
    }



}
