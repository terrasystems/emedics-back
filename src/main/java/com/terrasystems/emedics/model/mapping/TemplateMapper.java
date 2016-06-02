package com.terrasystems.emedics.model.mapping;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.dto.TemplateDto;

import java.io.IOException;

public class TemplateMapper {

    private ObjectMapper objectMapper;
    private static TemplateMapper mapper;

    public static TemplateMapper getInstance() {
        if(mapper == null) {
            mapper = new TemplateMapper();
            return mapper;
        }
        return mapper;
    }

    public TemplateMapper() {objectMapper = new ObjectMapper();}

    public TemplateDto toDto(Template entity) throws IOException  {
        TemplateDto dto = new TemplateDto();
        dto.setName(entity.getName());
        dto.setNumber(entity.getNumber());
        dto.setCategory(entity.getCategory());
        dto.setDescr(entity.getDescr());
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setBody(objectMapper.readTree(entity.getBody()));

        return dto;
    }

    public Template toEntity(TemplateDto dto) {
        Template entity = new Template();
        entity.setCategory(dto.getCategory());
        entity.setDescr(dto.getDescr());
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        return entity;
    }
}
