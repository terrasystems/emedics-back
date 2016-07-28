package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ReferenceDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.UserDto;
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

        userDoctor.setUserType(UserType.DOCTOR);
        when(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase("id", UserType.PATIENT, "a", "a")).thenReturn(users);
        when(utils.getCurrentUserId()).thenReturn("id");
        when(userRepository.findByEmail("id")).thenReturn(userDoctor);

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
        when(utils.getCurrentUserId()).thenReturn("id");
        when(userRepository.findByEmail("id")).thenReturn(userStaff);

        assertEquals(null, referencesServiceImpl.getAllReferences(criteriaDto));
    }

    @Test (expected = NullPointerException.class)
    public void test_will_return_null_pointer_exception() {
        User user = new User();
        when(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase("id", UserType.PATIENT, "a", "a")).thenReturn(users);
        when(utils.getCurrentUserId()).thenReturn("id");
        when(userRepository.findByEmail("id")).thenReturn(user);

        referencesServiceImpl.getAllReferences(new CriteriaDto());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void test_will_return_index_out_of_bounds_exception() {
        User user = new User();
        CriteriaDto criteriaDto = new CriteriaDto();
        criteriaDto.setSearch("a");
        criteriaDto.setType(UserType.PATIENT);

        user.setUserType(UserType.PATIENT);
        when(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase("id", UserType.PATIENT, "a", "a")).thenReturn(users);
        when(utils.getCurrentUserId()).thenReturn("id");
        when(userRepository.findByEmail("id")).thenReturn(user);

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

        userPatient.setUserType(UserType.PATIENT);
        when(userRepository.findByIdIsNotAndUserTypeAndNameContainingIgnoreCaseOrEmailContainingIgnoreCase("id", UserType.PATIENT, "a", "a")).thenReturn(users);
        when(utils.getCurrentUserId()).thenReturn("id");
        when(userRepository.findByEmail("id")).thenReturn(userPatient);

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
        when(utils.getCurrentUserId()).thenReturn("currentCurrentEmail");
        when(userRepository.findByEmail("currentCurrentEmail")).thenReturn(currentUser);
        when(userRepository.findById("user_to_ref_id")).thenReturn(userToRef);
        when(userRepository.save(currentUser)).thenReturn(null);
        when(userRepository.save(userToRef)).thenReturn(null);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_REF_ADDED.toString());

        assertEquals(responseDto.getMsg(), referencesServiceImpl.addReference("user_to_ref_id").getMsg());
        assertEquals(responseDto.getState(), referencesServiceImpl.addReference("user_to_ref_id").getState());

    }





}