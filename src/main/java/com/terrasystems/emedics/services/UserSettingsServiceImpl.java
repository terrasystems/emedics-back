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
        if (user.getUserType() != null) {
            return editSettings(user, dto);
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
        user.setAddress(dto.getAddress());
        user.setName(setName(dto));
        if (user.getOrganization() != null) {
            user = setOrg(user, dto);
        }
        if (dto.getType() != null) {
            user = setType(user, dto);
        }
        userRepository.save(user);
        UserMapper userMapper = UserMapper.getInstance();
        AuthDto authDto = new AuthDto(userMapper.toDTO(user), generateToken.generateToken(user));
        return utils.generateResponse(true, MessageEnums.MSG_USER_EDITED.toString(), authDto);
    }

    private User setType (User user, UserDto dto) {
        TypeMapper mapper = TypeMapper.getInstance();
        user.setType(mapper.toEntity(dto.getType()));
        return user;
    }

    private User setOrg(User user, UserDto dto) {
        user.getOrganization().setDescr(dto.getDescr());
        user.getOrganization().setWebsite(dto.getWebsite());
        user.getOrganization().setName(dto.getOrgName());
        return user;
    }

    public String setName(UserDto dto) {
        if ((dto.getFirstName() == null) && (dto.getLastName() == null)) {
            return dto.getEmail();
        } else if (dto.getFirstName() == null) {
            return dto.getLastName();
        } else if (dto.getLastName() == null) {
            return dto.getFirstName();
        } else return dto.getFirstName() + " " + dto.getLastName();
    }


}
