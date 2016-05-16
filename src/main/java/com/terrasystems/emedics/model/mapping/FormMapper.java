package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.UserForm;
import com.terrasystems.emedics.model.dto.UserFormDto;

public final class FormMapper {

    static public UserFormDto toDto(UserForm entity) {
        UserFormDto dto = new UserFormDto();
        //dto.setBody(entity.getBody());

        dto.setId(entity.getId());

        return dto;
    }

    static public UserForm toEntity(UserFormDto dto) {
        UserForm entity = new UserForm();
        //entity.setBody(dto.getBody());
        entity.setId(dto.getId());
        return entity;
    }
}
