package com.terrasystems.emedics.model.mapping;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.DocType;
import com.terrasystems.emedics.model.dto.DocTypeDto;

public class DocTypeMapper {

    private ObjectMapper objectMapper;
    private static DocTypeMapper mapper;

    public static DocTypeMapper getInstance() {
        if(mapper == null) {
            mapper = new DocTypeMapper();
            return mapper;
        }
        return mapper;
    }

    public DocTypeMapper() {objectMapper = new ObjectMapper();}

    public DocTypeDto toDto(DocType entity) {
        DocTypeDto dto = new DocTypeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        return dto;
    }

    public DocType toEntity(DocTypeDto dto) {
        DocType entity = new DocType();
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        return entity;
    }
}
