package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Form;
import com.terrasystems.emedics.model.dto.FormDto;

public final class FormMapper {

    static public FormDto toDto(Form entity) {
        FormDto dto = new FormDto();
        //dto.setBody(entity.getBody());

        dto.setId(entity.getId());

        return dto;
    }

    static public Form toEntity(FormDto dto) {
        Form entity = new Form();
        //entity.setBody(dto.getBody());
        entity.setId(dto.getId());
        return entity;
    }
}
