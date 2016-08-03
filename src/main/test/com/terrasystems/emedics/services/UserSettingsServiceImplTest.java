package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.Organization;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.*;
import com.terrasystems.emedics.model.mapping.UserMapper;
import com.terrasystems.emedics.security.JWT.JwtTokenUtil;
import com.terrasystems.emedics.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSettingsServiceImplTest {

    @InjectMocks
    UserSettingsServiceImpl userSettingsService;

    @Mock
    UserRepository userRepository;
    @Mock
    Utils utils;
    @Mock
    JwtTokenUtil generateToken;

    @Test
    public void test_will_user_not_found_for_getUserById() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);
        User user = null;

        when(userRepository.findOne("user")).thenReturn(user);
        when(utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.getUserById("user").getResult());
        assertEquals(responseDto.getState(), userSettingsService.getUserById("user").getState());
        assertEquals(responseDto.getMsg(), userSettingsService.getUserById("user").getMsg());

    }

    @Test
    public void test_will_return_user_by_id() {
        User user = new User();
        UserMapper userMapper = UserMapper.getInstance();
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_USER_BY_ID.toString(), userMapper.toDTO(user));

        when(userRepository.findOne("user")).thenReturn(user);
        when(utils.generateResponse(true, MessageEnums.MSG_USER_BY_ID.toString(), userMapper.toDTO(user))).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.getUserById("user").getResult());
        assertEquals(responseDto.getState(), userSettingsService.getUserById("user").getState());
        assertEquals(responseDto.getMsg(), userSettingsService.getUserById("user").getMsg());
    }

    @Test
    public void test_will_return_request_incorrect_for_changePassword_without_oldPass() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        User user = new User();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setNewPass("alalal");

        when(utils.getCurrentUser()).thenReturn(user);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.changePassword(changePasswordDto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.changePassword(changePasswordDto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.changePassword(changePasswordDto).getMsg());

    }

    @Test
    public void test_will_return_request_incorrect_for_changePassword_without_newPass() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        User user = new User();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPass("alalal");

        when(utils.getCurrentUser()).thenReturn(user);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.changePassword(changePasswordDto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.changePassword(changePasswordDto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.changePassword(changePasswordDto).getMsg());
    }

    @Test
    public void test_will_return_old_pass_are_wrong() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_OLD_PASS_IS_INCORRECT.toString(), null);
        User user = new User();
        user.setPassword("veryOld");
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setOldPass("old");
        changePasswordDto.setNewPass("new");

        when(utils.getCurrentUser()).thenReturn(user);
        when(utils.generateResponse(false, MessageEnums.MSG_OLD_PASS_IS_INCORRECT.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.changePassword(changePasswordDto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.changePassword(changePasswordDto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.changePassword(changePasswordDto).getMsg());
    }

    @Test
    public void test_will_change_password_for_user() {
        User user = new User();
        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setNewPass("old");
        changePasswordDto.setOldPass("old");
        user.setPassword("old");
        UserMapper userMapper = UserMapper.getInstance();
        AuthDto authDto = new AuthDto(userMapper.toDTO(user), "token");
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_PASS_CHANGED.toString(), authDto);

        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(null);
        when(generateToken.generateToken(user)).thenReturn("token");
        when(utils.generateResponse(true, MessageEnums.MSG_PASS_CHANGED.toString(), authDto)).thenReturn(responseDto);

        //TODO ?????????????????????????????????????????????????????????????????????????????????????????????
        assertEquals(responseDto.getResult(), userSettingsService.changePassword(changePasswordDto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.changePassword(changePasswordDto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.changePassword(changePasswordDto).getMsg());
    }

    @Test
    public void test_will_return_request_incorrect_for_editUser() {
        User user = new User();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        UserDto dto = new UserDto();

        when(utils.getCurrentUser()).thenReturn(user);
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);
        when(utils.isPatient(user)).thenReturn(false);
        when(utils.isDoctor(user)).thenReturn(false);
        when(utils.isStaff(user)).thenReturn(false);
        when(utils.isOrg(user)).thenReturn(false);

        assertEquals(responseDto.getResult(), userSettingsService.editUser(dto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.editUser(dto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.editUser(dto).getMsg());
    }

    @Test
    public void test_will_edit_settings_for_patient() {
        User user = new User();
        UserMapper userMapper = UserMapper.getInstance();
        AuthDto authDto = new AuthDto(userMapper.toDTO(user), "token");
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_USER_EDITED.toString(), authDto);
        UserDto dto = new UserDto();

        when(utils.isPatient(user)).thenReturn(true);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(null);
        when(generateToken.generateToken(user)).thenReturn("token");
        when(utils.generateResponse(true, MessageEnums.MSG_USER_EDITED.toString(), authDto)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.editUser(dto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.editUser(dto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.editUser(dto).getMsg());
    }

    @Test
    public void test_will_edit_settings_for_staff() {
        User user = new User();
        UserMapper userMapper = UserMapper.getInstance();
        AuthDto authDto = new AuthDto(userMapper.toDTO(user), "token");
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_USER_EDITED.toString(), authDto);
        UserDto dto = new UserDto();

        when(utils.isPatient(user)).thenReturn(false);
        when(utils.isStaff(user)).thenReturn(true);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(null);
        when(generateToken.generateToken(user)).thenReturn("token");
        when(utils.generateResponse(true, MessageEnums.MSG_USER_EDITED.toString(), authDto)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.editUser(dto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.editUser(dto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.editUser(dto).getMsg());
    }

    @Test
    public void test_will_edit_settings_for_doctor() {
        User user = new User();
        UserMapper userMapper = UserMapper.getInstance();
        AuthDto authDto = new AuthDto(userMapper.toDTO(user), "token");
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_USER_EDITED.toString(), authDto);
        UserDto dto = new UserDto();
        TypeDto typeDto = new TypeDto();
        dto.setType(typeDto);

        when(utils.isPatient(user)).thenReturn(false);
        when(utils.isStaff(user)).thenReturn(false);
        when(utils.isDoctor(user)).thenReturn(true);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(null);
        when(generateToken.generateToken(user)).thenReturn("token");
        when(utils.generateResponse(true, MessageEnums.MSG_USER_EDITED.toString(), authDto)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.editUser(dto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.editUser(dto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.editUser(dto).getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_editUser_without_type_for_doctor() {
        User user = new User();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        UserDto dto = new UserDto();

        when(utils.isPatient(user)).thenReturn(false);
        when(utils.isStaff(user)).thenReturn(false);
        when(utils.isDoctor(user)).thenReturn(true);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(null);
        when(generateToken.generateToken(user)).thenReturn("token");
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.editUser(dto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.editUser(dto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.editUser(dto).getMsg());
    }

    @Test
    public void test_will_edit_settings_for_admin() {
        User user = new User();
        UserMapper userMapper = UserMapper.getInstance();
        AuthDto authDto = new AuthDto(userMapper.toDTO(user), "token");
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_USER_EDITED.toString(), authDto);
        UserDto dto = new UserDto();
        TypeDto typeDto = new TypeDto();
        dto.setType(typeDto);
        Organization organization = new Organization();
        user.setOrganization(organization);

        when(utils.isPatient(user)).thenReturn(false);
        when(utils.isStaff(user)).thenReturn(false);
        when(utils.isDoctor(user)).thenReturn(false);
        when(utils.isOrg(user)).thenReturn(true);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(null);
        when(generateToken.generateToken(user)).thenReturn("token");
        when(utils.generateResponse(true, MessageEnums.MSG_USER_EDITED.toString(), authDto)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.editUser(dto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.editUser(dto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.editUser(dto).getMsg());
    }

    @Test
    public void test_will_return_user_have_no_org() {
        User user = new User();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_USER_HAVE_NO_ORG.toString(), null);
        UserDto dto = new UserDto();
        TypeDto typeDto = new TypeDto();
        dto.setType(typeDto);

        when(utils.isPatient(user)).thenReturn(false);
        when(utils.isStaff(user)).thenReturn(false);
        when(utils.isDoctor(user)).thenReturn(false);
        when(utils.isOrg(user)).thenReturn(true);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(null);
        when(generateToken.generateToken(user)).thenReturn("token");
        when(utils.generateResponse(false, MessageEnums.MSG_USER_HAVE_NO_ORG.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.editUser(dto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.editUser(dto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.editUser(dto).getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_editUser_without_type_for_org() {
        User user = new User();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        UserDto dto = new UserDto();
        Organization organization = new Organization();
        user.setOrganization(organization);

        when(utils.isPatient(user)).thenReturn(false);
        when(utils.isStaff(user)).thenReturn(false);
        when(utils.isDoctor(user)).thenReturn(false);
        when(utils.isOrg(user)).thenReturn(true);
        when(utils.getCurrentUser()).thenReturn(user);
        when(userRepository.save(user)).thenReturn(null);
        when(generateToken.generateToken(user)).thenReturn("token");
        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.editUser(dto).getResult());
        assertEquals(responseDto.getState(), userSettingsService.editUser(dto).getState());
        assertEquals(responseDto.getMsg(), userSettingsService.editUser(dto).getMsg());
    }

    @Test
    public void test_will_return_not_supported_for_remove() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_NOT_SUPPORTED.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_NOT_SUPPORTED.toString(), null)).thenReturn(responseDto);

        assertEquals(responseDto.getResult(), userSettingsService.removeUser("").getResult());
        assertEquals(responseDto.getState(), userSettingsService.removeUser("").getState());
        assertEquals(responseDto.getMsg(), userSettingsService.removeUser("").getMsg());
    }


}
