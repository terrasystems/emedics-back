package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dtoV2.ReferenceDto;

import java.util.List;
import java.util.stream.Collectors;

public class ReferenceConverter {

    public List<ReferenceDto> convertFromUsers(List<User> users) {
        List<ReferenceDto> referenceDtos = users.stream()
                .map(user -> {
                    ReferenceDto ref = new ReferenceDto();
                    TypeMapper mapper = TypeMapper.getInstance();
                    ref.setId(user.getId());
                    if (user.getType() == null) {
                        ref.setType(null);
                    } else {
                        ref.setType(mapper.toDto(user.getType()));
                    }
                    ref.setName(user.getName());
                    ref.setFirstName(user.getFirstName());
                    ref.setLastName(user.getLastName());
                    ref.setDob(user.getBirth());
                    ref.setUserType(user.getUserType());
                    ref.setEmail(user.getEmail());
                    ref.setPhone(user.getPhone());
                    ref.setActive(user.isEnabled());
                    return ref;
                }).collect(Collectors.toList());
        return referenceDtos;
    }
}
