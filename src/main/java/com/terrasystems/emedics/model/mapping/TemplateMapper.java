package com.terrasystems.emedics.model.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.dtoV2.TemplateDto;

import java.io.IOException;

public class TemplateMapper {
    private static TemplateMapper mapper;
    private ObjectMapper objectMapper;

    public static TemplateMapper getInstance() {
        if (mapper == null) {
            mapper = new TemplateMapper();
            return mapper;
        }
        return mapper;
    }

    public TemplateDto toDto(Template entity) throws IOException {
        objectMapper = new ObjectMapper();
        TemplateDto dto = new TemplateDto();
        dto.setName(entity.getName());
        dto.setNumber(entity.getNumber());
        dto.setDescr(entity.getDescr());
        dto.setId(entity.getId());
        dto.setBody(objectMapper.readTree(entity.getBody()));
        dto.setCommerce(entity.isCommercialEnum());
        dto.setType(entity.getTypeEnum());
        return dto;
    }
}
