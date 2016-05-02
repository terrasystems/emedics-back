package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.dto.FormDto;

public final class FormMapper {

    static public FormDto toDto(Form entity) {
        FormDto dto = new FormDto();
        dto.setBody(entity.getBody());
        dto.setCategory(entity.getCategory());
        dto.setDescr(entity.getDescr());
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        return dto;
    }

    static public Form toEntity(FormDto dto) {
        Form entity = new Form();
        entity.setBody(dto.getBody());
        entity.setCategory(dto.getCategory());
        entity.setDescr(dto.getDescr());
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        return entity;
    }
}
