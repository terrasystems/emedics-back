package com.terrasystems.emedics.model.mapping;

import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.UserDto;


public final class UserMapper {

    private  static UserMapper mapper;

    public static UserMapper getInstance() {
        if (mapper == null) {
            mapper = new UserMapper();
            return mapper;
        }
        return mapper;
    }

    public UserDto toDTO(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getName());
        return dto;
    }

    public User toEntity(UserDto dto) {
        User entity = new User();
        entity.setEmail(dto.getEmail());
        //entity.setDiscriminatorValue(dto.getType());  //methods dto.getType(), setDiscriminatorValue() not supported yet
        //entity.setUsername(dto.getUsername());    //method getUsername() not supported yet
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
