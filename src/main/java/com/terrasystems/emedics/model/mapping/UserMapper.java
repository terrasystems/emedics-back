package com.terrasystems.emedics.model.mapping;

import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.UserDto;


public final class UserMapper {

    public static UserDto toDTO(User entity) {
        UserDto dto = new UserDto();
        dto.setEmail(entity.getEmail());
        dto.setType(entity.getDiscriminatorValue()); //without test
        dto.setUsername(entity.getUsername()); //without test
        dto.setPassword(entity.getPassword());
        return dto;
    }

    public static User toEntity(UserDto dto) {
        User entity = new User();
        entity.setEmail(dto.getEmail());
        //entity.setDiscriminatorValue(dto.getType());  //methods dto.getType(), setDiscriminatorValue() not supported yet
        //entity.setUsername(dto.getUsername());    //method getUsername() not supported yet
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
