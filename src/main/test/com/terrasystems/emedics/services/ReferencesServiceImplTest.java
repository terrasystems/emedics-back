package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ReferenceDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.mapping.ReferenceConverter;
import com.terrasystems.emedics.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReferencesServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private Utils utils;


    @InjectMocks
    ReferencesServiceImpl referencesServiceImpl = new ReferencesServiceImpl();

    private List<User> users = new ArrayList<>();


    @Test
    public void test_will_return_all_references_where_current_is_DOCTOR() {
        User userDoctor = new User();
        CriteriaDto criteriaDto = new CriteriaDto();
        criteriaDto.setSearch("a");
        criteriaDto.setType(UserType.DOCTOR);
        ReferenceConverter converter = new ReferenceConverter();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_DOC_REF.toString(), converter.convertFromUsers(users));
        List<ReferenceDto> refs = new ArrayList<>();

        userDoctor.setUserType(UserType.DOCTOR);
        when(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase("id", UserType.PATIENT, "a", "a")).thenReturn(users);
        when(utils.getCurrentUser()).thenReturn(userDoctor);
        when(userRepository.findByEmail("id")).thenReturn(userDoctor);
        when(utils.generateResponse(true, MessageEnums.MSG_DOC_REF.toString(), refs)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), referencesServiceImpl.getAllReferences(criteriaDto).getResult());
        assertEquals(responseDto.getState(), referencesServiceImpl.getAllReferences(criteriaDto).getState());
        assertEquals(responseDto.getMsg(), referencesServiceImpl.getAllReferences(criteriaDto).getMsg());
    }

    @Test
    public void test_will_return_all_references_where_current_is_STAFF() {
        User userStaff = new User();
        CriteriaDto criteriaDto = new CriteriaDto();
        criteriaDto.setSearch("a");
        criteriaDto.setType(UserType.STAFF);

        userStaff.setUserType(UserType.STAFF);
        when(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase("id", UserType.PATIENT, "a", "a")).thenReturn(users);
        when(utils.getCurrentUser()).thenReturn(userStaff);
        when(userRepository.findByEmail("id")).thenReturn(userStaff);

        assertEquals(null, referencesServiceImpl.getAllReferences(criteriaDto));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void test_will_return_index_out_of_bounds_exception() {
        User user = new User();
        CriteriaDto criteriaDto = new CriteriaDto();
        criteriaDto.setSearch("a");
        criteriaDto.setType(UserType.PATIENT);
        List<ReferenceDto> refs = new ArrayList<>();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_PAT_REF.toString(), refs);

        user.setUserType(UserType.PATIENT);
        when(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase("id", UserType.PATIENT, "a", "a")).thenReturn(users);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.findByEmail("id")).thenReturn(user);
        when(utils.generateResponse(true, MessageEnums.MSG_PAT_REF.toString(), refs)).thenReturn(responseDto);

        List<ReferenceDto> referenceDtos = (List<ReferenceDto>) referencesServiceImpl.getAllReferences(criteriaDto).getResult();
        referenceDtos.get(0);
    }

    @Test
    public void test_will_return_all_references_where_current_is_PATIENT() {
        User userPatient = new User();
        CriteriaDto criteriaDto = new CriteriaDto();
        criteriaDto.setSearch("a");
        criteriaDto.setType(UserType.PATIENT);
        ReferenceConverter converter = new ReferenceConverter();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_PAT_REF.toString(), converter.convertFromUsers(users));
        List<ReferenceDto> refs = new ArrayList<>();

        userPatient.setUserType(UserType.PATIENT);
        when(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase("id", UserType.PATIENT, "a", "a")).thenReturn(users);
        when(utils.getCurrentUser()).thenReturn(userPatient);
        when(userRepository.findByEmail("id")).thenReturn(userPatient);
        when(utils.generateResponse(true, MessageEnums.MSG_PAT_REF.toString(), refs)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), referencesServiceImpl.getAllReferences(criteriaDto).getResult());
        assertEquals(responseDto.getState(), referencesServiceImpl.getAllReferences(criteriaDto).getState());
        assertEquals(responseDto.getMsg(), referencesServiceImpl.getAllReferences(criteriaDto).getMsg());
    }

    @Test
    public void test_will_add_new_reference_to_set_references_both_users() {
        User currentUser = new User();
        currentUser.setReferences(new HashSet<>());
        currentUser.setEmail("currentCurrentEmail");
        User userToRef = new User();
        userToRef.setId("user_to_ref_id");
        userToRef.setReferences(new HashSet<>());
        Set<User> referencesCurrent = currentUser.getReferences();
        Set<User> referencesToSave = userToRef.getReferences();
        referencesToSave.add(currentUser);
        referencesCurrent.add(userToRef);
        when(utils.getCurrentUser()).thenReturn(currentUser);
        when(userRepository.findByEmail("currentCurrentEmail")).thenReturn(currentUser);
        when(userRepository.findById("user_to_ref_id")).thenReturn(userToRef);
        when(userRepository.save(currentUser)).thenReturn(null);
        when(userRepository.save(userToRef)).thenReturn(null);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_REF_ADDED.toString());
        when(utils.generateResponse(true, MessageEnums.MSG_REF_ADDED.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.addReference("user_to_ref_id").getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.addReference("user_to_ref_id").getState());
    }

    @Test
    public void test_will_return_message_user_not_found_current_is_null_for_add() {
        User currentUser = null;
        User userToRef = new User();

        when(utils.getCurrentUser()).thenReturn(currentUser);
        when(userRepository.findByEmail("currentCurrentEmail")).thenReturn(currentUser);
        when(userRepository.findById("user_to_ref_id")).thenReturn(userToRef);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_USER_NOT_FOUND.toString());
        when(utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.addReference("user_to_ref_id").getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.addReference("user_to_ref_id").getState());
    }

    @Test
    public void test_will_return_message_user_not_found_userToRef_is_null_for_add() {
        User currentUser = new User();
        User userToRef = null;

        when(utils.getCurrentUser()).thenReturn(currentUser);
        when(userRepository.findByEmail("currentCurrentEmail")).thenReturn(currentUser);
        when(userRepository.findById("user_to_ref_id")).thenReturn(userToRef);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_USER_NOT_FOUND.toString());
        when(utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.addReference("user_to_ref_id").getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.addReference("user_to_ref_id").getState());
    }

    @Test
    public void test_will_remove_reference_to_both_users() {
        User currentUser = new User();
        currentUser.setReferences(new HashSet<>());
        currentUser.setEmail("currentCurrentEmail");
        User userToRef = new User();
        userToRef.setId("user_to_ref_id");
        userToRef.setReferences(new HashSet<>());
        Set<User> referencesCurrent = currentUser.getReferences();
        Set<User> referencesToSave = userToRef.getReferences();
        referencesToSave.add(currentUser);
        referencesCurrent.add(userToRef);
        when(utils.getCurrentUser()).thenReturn(currentUser);
        when(userRepository.findByEmail("currentCurrentEmail")).thenReturn(currentUser);
        when(userRepository.findById("user_to_ref_id")).thenReturn(userToRef);
        when(userRepository.save(currentUser)).thenReturn(null);
        when(userRepository.save(userToRef)).thenReturn(null);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_REF_REMOVED.toString());
        when(utils.generateResponse(true, MessageEnums.MSG_REF_REMOVED.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.removeReference("user_to_ref_id").getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.removeReference("user_to_ref_id").getState());
    }

    @Test
    public void test_will_return_message_user_not_found_current_is_null_for_remove() {
        User currentUser = null;
        User userToRef = new User();

        when(utils.getCurrentUser()).thenReturn(currentUser);
        when(userRepository.findByEmail("currentCurrentEmail")).thenReturn(currentUser);
        when(userRepository.findById("user_to_ref_id")).thenReturn(userToRef);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_USER_NOT_FOUND.toString());
        when(utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.removeReference("user_to_ref_id").getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.removeReference("user_to_ref_id").getState());
    }

    @Test
    public void test_will_return_message_user_not_found_userToRef_is_null_for_remove() {
        User currentUser = new User();
        User userToRef = null;

        when(utils.getCurrentUser()).thenReturn(currentUser);
        when(userRepository.findByEmail("currentCurrentEmail")).thenReturn(currentUser);
        when(userRepository.findById("user_to_ref_id")).thenReturn(userToRef);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_USER_NOT_FOUND.toString());
        when(utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.removeReference("user_to_ref_id").getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.removeReference("user_to_ref_id").getState());
    }

    @Test
    public void test_will_return_message_email_exist() {
        User existUser = new User();
        ReferenceDto referenceDto = new ReferenceDto();
        referenceDto.setEmail("exist_email");
        when(userRepository.findByEmail("current_id")).thenReturn(existUser);
        when(utils.getCurrentUser()).thenReturn(existUser);
        when(userRepository.existsByEmail("exist_email")).thenReturn(true);
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_EMAIL_EXIST.toString(), null);
        when(utils.generateResponse(false, MessageEnums.MSG_EMAIL_EXIST.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.createReference(referenceDto).getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.createReference(referenceDto).getState());
    }

    @Test
    public void test_will_return_patient_create_doctor() {
        User user = new User();
        user.setReferences(new HashSet<>());
        User userDoctor = new User();
        user.setUserType(UserType.PATIENT);
        ReferenceDto referenceDto = new ReferenceDto();
        referenceDto.setUserType(UserType.DOCTOR);
        referenceDto.setId("not_exist_id");
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_REF_CREATED.toString(), null);
        when(userRepository.findByEmail("current_id")).thenReturn(user);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.existsByEmail("not_exist_id")).thenReturn(false);
        when(userRepository.save(userDoctor)).thenReturn(null);
        when(utils.generateResponse(true, MessageEnums.MSG_REF_CREATED.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.createReference(referenceDto).getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.createReference(referenceDto).getState());
    }

    @Test
    public void test_will_return_doctor_create_patient() {
        User user = new User();
        user.setReferences(new HashSet<>());
        User userDoctor = new User();
        user.setUserType(UserType.DOCTOR);
        ReferenceDto referenceDto = new ReferenceDto();
        referenceDto.setUserType(UserType.PATIENT);
        referenceDto.setId("not_exist_id");
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_REF_CREATED.toString(), null);
        when(userRepository.findByEmail("current_id")).thenReturn(user);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.existsByEmail("not_exist_id")).thenReturn(false);
        when(userRepository.save(userDoctor)).thenReturn(null);
        when(utils.generateResponse(true, MessageEnums.MSG_REF_CREATED.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.createReference(referenceDto).getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.createReference(referenceDto).getState());
    }

    @Test
    public void test_will_return_doctor_create_doctor() {
        User user = new User();
        user.setReferences(new HashSet<>());
        User userDoctor = new User();
        user.setUserType(UserType.DOCTOR);
        ReferenceDto referenceDto = new ReferenceDto();
        referenceDto.setUserType(UserType.DOCTOR);
        referenceDto.setId("not_exist_id");
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_REF_CREATED.toString(), null);
        when(userRepository.findByEmail("current_id")).thenReturn(user);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.existsByEmail("not_exist_id")).thenReturn(false);
        when(userRepository.save(userDoctor)).thenReturn(null);
        when(utils.generateResponse(true, MessageEnums.MSG_REF_CREATED.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.createReference(referenceDto).getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.createReference(referenceDto).getState());
    }

    @Test
    public void test_return_null_if_type_not_doctor_or_not_patient() {
        User user = new User();
        user.setReferences(new HashSet<>());
        ReferenceDto referenceDto = new ReferenceDto();
        referenceDto.setUserType(UserType.DOCTOR);
        referenceDto.setId("not_exist_id");
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_NOT_SUPPORTED.toString(), null);
        when(userRepository.findByEmail("current_id")).thenReturn(user);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.existsByEmail("not_exist_id")).thenReturn(false);
        when(utils.generateResponse(false, MessageEnums.MSG_NOT_SUPPORTED.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getMsg(), referencesServiceImpl.createReference(referenceDto).getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.createReference(referenceDto).getState());
    }
}