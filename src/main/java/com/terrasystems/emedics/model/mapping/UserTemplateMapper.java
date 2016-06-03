package com.terrasystems.emedics.model.mapping;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.UserTemplate;
import com.terrasystems.emedics.model.dto.UserTemplateDto;

import java.io.IOException;

public class UserTemplateMapper {
    private ObjectMapper objectMapper;
    private static UserTemplateMapper mapper;

    public static UserTemplateMapper getInstance() {
        if(mapper == null) {
            mapper = new UserTemplateMapper();
            return mapper;
        }
        return mapper;
    }

    public UserTemplateMapper() {objectMapper = new ObjectMapper();}

    public UserTemplateDto toDto(UserTemplate entity) throws IOException {
        TemplateMapper mapper = TemplateMapper.getInstance();
        UserTemplateDto dto = new UserTemplateDto();
        dto.setDescription(entity.getDescription());
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setTemplateDto(mapper.toDto(entity.getTemplate()));
        return dto;
    }

    public UserTemplate toEntity(UserTemplateDto dto) {
        UserTemplate entity = new UserTemplate();
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        return entity;
    }
}
