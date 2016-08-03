package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.UserRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.AuthDto;
import com.terrasystems.emedics.model.dtoV2.ChangePasswordDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.UserDto;
import com.terrasystems.emedics.model.mapping.TypeMapper;
import com.terrasystems.emedics.model.mapping.UserMapper;
import com.terrasystems.emedics.security.JWT.JwtTokenUtil;
import com.terrasystems.emedics.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsServiceImpl implements UserSettingsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Autowired
    private JwtTokenUtil generateToken;

    @Override
    public ResponseDto getUserById(String id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            return utils.generateResponse(false, MessageEnums.MSG_USER_NOT_FOUND.toString(), null);
        }
        UserMapper userMapper = UserMapper.getInstance();
        return utils.generateResponse(true, MessageEnums.MSG_USER_BY_ID.toString(), userMapper.toDTO(user));
    }

    @Override
    public ResponseDto changePassword(ChangePasswordDto dto) {
        User user = utils.getCurrentUser();
        if (dto.getNewPass() == null || dto.getOldPass() == null) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        }
        if (!dto.getOldPass().equals(user.getPassword())) {
            return utils.generateResponse(false, MessageEnums.MSG_OLD_PASS_IS_INCORRECT.toString(), null);
        }
        user.setPassword(dto.getNewPass());

        userRepository.save(user);
        UserMapper userMapper = UserMapper.getInstance();
        AuthDto authDto = new AuthDto(userMapper.toDTO(user), generateToken.generateToken(user));
        return utils.generateResponse(true, MessageEnums.MSG_PASS_CHANGED.toString(), authDto);
    }

    @Override
    public ResponseDto editUser(UserDto dto) {
        User user = utils.getCurrentUser();
        if (utils.isPatient(user) || utils.isStaff(user)) {
            return editSettings(user, dto);
        }
        if (utils.isDoctor(user)) {
            return editSettingsForDoctor(user, dto);
        }
        if (utils.isOrg(user)) {
            return editSettingsForOrg(user, dto);
        }
        return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
    }

    @Override
    public ResponseDto removeUser(String id) {
        return utils.generateResponse(false, MessageEnums.MSG_NOT_SUPPORTED.toString(), null);
    }

    private ResponseDto editSettings(User user, UserDto dto) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setBirth(dto.getDob());
        userRepository.save(user);
        UserMapper userMapper = UserMapper.getInstance();
        AuthDto authDto = new AuthDto(userMapper.toDTO(user), generateToken.generateToken(user));
        return utils.generateResponse(true, MessageEnums.MSG_USER_EDITED.toString(), authDto);
    }

    private ResponseDto editSettingsForDoctor(User user, UserDto dto) {
        if (dto.getType() == null) {
            return utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);
        }
        TypeMapper mapper = TypeMapper.getInstance();
        user.setType(mapper.toEntity(dto.getType()));
        return editSettings(user, dto);
    }

    private ResponseDto editSettingsForOrg(User user, UserDto dto) {
        if (user.getOrganization() == null) {
            return utils.generateResponse(false, MessageEnums.MSG_USER_HAVE_NO_ORG.toString(), null);
        }
        user.getOrganization().setDescr(dto.getDescr());
        user.getOrganization().setWebsite(dto.getWebsite());
        user.getOrganization().setName(dto.getOrgName());
        user.getOrganization().setAddress(dto.getAddress());
        return editSettingsForDoctor(user, dto);
    }


}
