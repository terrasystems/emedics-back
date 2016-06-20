package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Stuff;
import com.terrasystems.emedics.model.dto.StuffDto;

public class StuffMapper {
    private static StuffMapper mapper;

    public static StuffMapper getInstance() {
        if (mapper == null) {
            mapper = new StuffMapper();
            return mapper;
        }
        return mapper;
    }
    public StuffDto toDto(Stuff entity) {
        StuffDto dto = new StuffDto();
        dto.setBirth(entity.getBirth());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setId(entity.getId());
        return dto;
    }
}