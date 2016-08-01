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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReferencesServiceImpl implements ReferencesService {

    @Autowired
    Utils utils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;

    @Override
    @Transactional
    public ResponseDto getAllReferences(CriteriaDto criteriaDto) {
        if (criteriaDto.getSearch() == null) {
            return utils.generateResponse(false, MessageEnums.MSG_SEARCH_IS_NULL.toString(), null);
        }
        User currentUser = utils.getCurrentUser();
        if (isPatient(currentUser)) {
            return getAllReferencesForPatient(currentUser, criteriaDto);
        }
        if (isDoctor(currentUser) || isOrg(currentUser)) {
            return getAllReferencesForDoctorOrOrg(currentUser, criteriaDto);
        }
        if (isStaff(currentUser)){
            return getAllReferencesForStaff(currentUser, criteriaDto);
        }
        return utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);
    }

    @Override
    @Transactional
    public ResponseDto myReferences(CriteriaDto criteriaDto) {
        if (criteriaDto.getSearch() == null) {
            return utils.generateResponse(false, MessageEnums.MSG_SEARCH_IS_NULL.toString(), null);
        }
        User currentUser = utils.getCurrentUser();
        if (isPatient(currentUser)) {
            return myReferencesForPatient(currentUser, criteriaDto);
        }
        if (isDoctor(currentUser) || isOrg(currentUser)) {
            return myReferencesForDoctorOrOrg(currentUser, criteriaDto);
        }
        if (isStaff(currentUser)) {
            return myReferencesForStaff(currentUser, criteriaDto);
        }

        return utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);
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
    @Transactional
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

    @Override
    @Transactional
    public ResponseDto getReferenceById(String id) {
        if (id == null) {
            return utils.generateResponse(false, MessageEnums.MSG_BAD_REQUEST.toString(), null);
        }
        ReferenceConverter converter = new ReferenceConverter();
        User currentUser = utils.getCurrentUser();
        List<User> currentRefs = currentUser.getReferences().stream()
                .filter(ref -> ref.getId().equals(id))
                .collect(Collectors.toList());
        if (currentRefs.isEmpty()) {
            return utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);
        }
        return utils.generateResponse(true, MessageEnums.MSG_REF_BY_ID.toString(), converter.convertFromUsers(currentRefs));
    }

    @Override
    @Transactional
    public ResponseDto invite(String id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);
        }
        return mailService.sendRegistrationMail(user.getEmail(),user.getActivationToken(),user.getPassword());
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

        String activateToken = createActivationMark();
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
        String activateToken = createActivationMark();
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

    private String createActivationMark() {
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

    private ResponseDto getAllReferencesForPatient(User currentUser, CriteriaDto criteriaDto) {
        List<ReferenceDto> refs = new ArrayList<>();
        ReferenceConverter converter = new ReferenceConverter();
        refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase())));
        refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase())));
        return utils.generateResponse(true, MessageEnums.MSG_PAT_REF.toString(), refs);
    }

    private ResponseDto getAllReferencesForDoctorOrOrg(User currentUser, CriteriaDto criteriaDto) {
        List<ReferenceDto> refs = new ArrayList<>();
        ReferenceConverter converter = new ReferenceConverter();
        refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase())));
        refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase())));
        refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase())));
        return utils.generateResponse(true, MessageEnums.MSG_DOC_REF.toString(), refs);
    }

    private ResponseDto getAllReferencesForStaff(User currentUser, CriteriaDto criteriaDto) {
        List<ReferenceDto> refs = new ArrayList<>();
        ReferenceConverter converter = new ReferenceConverter();
        refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getUserOrg().getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase())));
        refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getUserOrg().getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase())));
        refs.addAll(converter.convertFromUsers(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getUserOrg().getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase())));
        return utils.generateResponse(true, MessageEnums.MSG_STAFF_REF.toString(), refs);
    }

    private ResponseDto myReferencesForPatient(User currentUser, CriteriaDto criteriaDto) {
        ReferenceConverter converter = new ReferenceConverter();
        Set<User> currentRefs = currentUser.getReferences();
        List<ReferenceDto> refs = new ArrayList<>();
        List<User> doctorsRefs = userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase()).stream()
                .filter(doctor -> currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<User> orgsRefs = userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase()).stream()
                .filter(org -> currentRefs.contains(org))
                .collect(Collectors.toList());
        refs.addAll(converter.convertFromUsers(doctorsRefs));
        refs.addAll(converter.convertFromUsers(orgsRefs));
        return utils.generateResponse(true, MessageEnums.MSG_PAT_REF.toString(), refs);
    }

    private ResponseDto myReferencesForDoctorOrOrg(User currentUser, CriteriaDto criteriaDto) {
        ReferenceConverter converter = new ReferenceConverter();
        Set<User> currentRefs = currentUser.getReferences();
        List<ReferenceDto> refs = new ArrayList<>();
        List<User> doctorsRefs = userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase()).stream()
                .filter(doctor -> currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<User> patientsRefs = userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase()).stream()
                .filter(doctor -> currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<User> orgsRefs = userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase()).stream()
                .filter(org -> currentRefs.contains(org))
                .collect(Collectors.toList());
        refs.addAll(converter.convertFromUsers(doctorsRefs));
        refs.addAll(converter.convertFromUsers(orgsRefs));
        refs.addAll(converter.convertFromUsers(patientsRefs));
        return utils.generateResponse(true, MessageEnums.MSG_DOC_REF.toString(), refs);
    }

    private ResponseDto myReferencesForStaff(User currentUser, CriteriaDto criteriaDto) {
        ReferenceConverter converter = new ReferenceConverter();
        Set<User> currentRefs = currentUser.getReferences();
        List<ReferenceDto> refs = new ArrayList<>();
        List<User> doctorsRefs = userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getUserOrg().getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.DOCTOR, criteriaDto.getSearch().toLowerCase()).stream()
                .filter(doctor -> currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<User> patientsRefs = userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getUserOrg().getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.PATIENT, criteriaDto.getSearch().toLowerCase()).stream()
                .filter(doctor -> currentRefs.contains(doctor))
                .collect(Collectors.toList());
        List<User> orgsRefs = userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrIdIsNotAndUserTypeAndEmailContainingIgnoreCaseOrIdIsNotAndUserTypeAndType_NameContainingIgnoreCase(currentUser.getUserOrg().getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase(), currentUser.getUserOrg().getId(), UserType.ORG, criteriaDto.getSearch().toLowerCase()).stream()
                .filter(org -> currentRefs.contains(org))
                .collect(Collectors.toList());
        refs.addAll(converter.convertFromUsers(doctorsRefs));
        refs.addAll(converter.convertFromUsers(orgsRefs));
        refs.addAll(converter.convertFromUsers(patientsRefs));
        return utils.generateResponse(true, MessageEnums.MSG_STAFF_REF.toString(), refs);
    }

    private boolean isPatient(User current) {
        return UserType.PATIENT.equals(current.getUserType());
    }

    private boolean isDoctor(User current) {
        return UserType.DOCTOR.equals(current.getUserType());
    }

    private boolean isStaff(User current) {
        return UserType.STAFF.equals(current.getUserType());
    }

    private boolean isOrg(User current) {
        return UserType.ORG.equals(current.getUserType());
    }


}
