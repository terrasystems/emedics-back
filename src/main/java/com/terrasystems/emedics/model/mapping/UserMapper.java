package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.UserDto;

public class UserMapper {

    private  static UserMapper mapper;

    public static UserMapper getInstance() {
        if (mapper == null) {
            mapper = new UserMapper();
            return mapper;
        }
        return mapper;
    }

    public UserDto toDTO(User entity) {
        TypeMapper mapper = TypeMapper.getInstance();
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUserType(entity.getUserType());
        dto.setType(mapper.toDto(entity.getType()));
        dto.setName(entity.getName());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDob(entity.getBirth());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAdmin(entity.isAdmin());
        if (dto.getAdmin()) {
            dto.setAddress(entity.getOrganization().getAddress());
            dto.setOrgName(entity.getOrganization().getName());
            dto.setWebsite(entity.getOrganization().getWebsite());
            dto.setDescr(entity.getOrganization().getDescr());
        }
        return dto;
    }
}
