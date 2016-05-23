package com.terrasystems.emedics.model.mapping;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.UserForm;
import com.terrasystems.emedics.model.dto.UserFormDto;

import java.io.IOException;

public final class FormMapper {
    private ObjectMapper objectMapper;
    private static FormMapper mapper;

    public static FormMapper getInstance() {
        if (mapper == null) {
            mapper = new FormMapper();
            return mapper;
        }
        return mapper;
    }
    public FormMapper() {
        objectMapper = new ObjectMapper();
    }

     public UserFormDto toDto(UserForm entity) throws IOException {
        BlankMapper mapper = BlankMapper.getInstance();
        UserFormDto dto = new UserFormDto();
        dto.setBlank(mapper.toDto(entity.getBlank()));
        dto.setId(entity.getId());
        dto.setActive(entity.isActive());
        dto.setData(objectMapper.readTree(entity.getData()));
        return dto;
    }

     public UserForm toEntity(UserFormDto dto) {
        UserForm entity = new UserForm();
        //entity.setBody(dto.getBody());
        entity.setId(dto.getId());
        return entity;
    }
}
