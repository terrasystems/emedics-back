package com.terrasystems.emedics.model.mapping;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.Blank;
import com.terrasystems.emedics.model.dto.BlankDto;

import java.io.IOException;

public final class  BlankMapper {
    private ObjectMapper objectMapper;
    private static BlankMapper mapper;

    public static BlankMapper getInstance() {
        if (mapper == null) {
            mapper = new BlankMapper();
            return mapper;
        }
        return mapper;
    }
    public BlankMapper() {
        objectMapper = new ObjectMapper();
    }
    public BlankDto toDto(Blank entity) throws IOException {
        BlankDto dto = new BlankDto();
        dto.setCategory(entity.getCategory());
        dto.setDescr(entity.getDescr());
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setBody(objectMapper.readTree(entity.getBody()));

        return dto;
    }

    public Blank toEntity(BlankDto dto) {
        Blank entity = new Blank();
        //entity.setBody(dto.getBody());
        entity.setCategory(dto.getCategory());
        entity.setDescr(dto.getDescr());
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        //entity.setUserForms(dto.getUserForms());
        return entity;
    }
}
