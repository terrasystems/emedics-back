package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Blank;
import com.terrasystems.emedics.model.dto.BlankDto;

public final class  BlankMapper {

    public static BlankDto toDto(Blank entity) {
        BlankDto dto = new BlankDto();
        //dto.setBody(entity.getBody());
        dto.setCategory(entity.getCategory());
        dto.setDescr(entity.getDescr());
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        //dto.setForms(entity.getForms());
        return dto;
    }

    public static Blank toEntity(BlankDto dto) {
        Blank entity = new Blank();
        //entity.setBody(dto.getBody());
        entity.setCategory(dto.getCategory());
        entity.setDescr(dto.getDescr());
        entity.setId(dto.getId());
        entity.setType(dto.getType());
        //entity.setForms(dto.getForms());
        return entity;
    }
}
