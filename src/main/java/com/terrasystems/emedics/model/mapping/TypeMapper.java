package com.terrasystems.emedics.model.mapping;

import com.terrasystems.emedics.model.Types;
import com.terrasystems.emedics.model.dtoV2.TypeDto;

public class TypeMapper {

    private  static TypeMapper mapper;

    public static TypeMapper getInstance() {
        if (mapper == null) {
            mapper = new TypeMapper();
            return mapper;
        }
        return mapper;
    }

    public TypeDto toDto(Types types) {
        TypeDto dto = new TypeDto();
        dto.setUserType(types.getUserType());
        dto.setName(types.getName());
        dto.setId(types.getId());
        return dto;
    }

    public Types toEntity(TypeDto dto) {
        Types type = new Types();
        type.setUserType(dto.getUserType());
        type.setName(dto.getName());
        type.setId(dto.getId());
        return type;
    }
}
