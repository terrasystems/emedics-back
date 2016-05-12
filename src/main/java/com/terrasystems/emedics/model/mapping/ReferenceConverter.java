package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.ReferenceDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferenceConverter {

    public List<ReferenceDto> convertFromDoctors(List<Doctor> doctors) {
        List<ReferenceDto> references = doctors.stream()
                .map(doctor -> {
                    ReferenceDto ref = new ReferenceDto();
                    ref.setId(doctor.getId());
                    ref.setType(doctor.getType());
                    ref.setName(doctor.getName());
                    ref.setPhone(doctor.getPhone());
                    ref.setEmail(doctor.getEmail());
                    return ref;
                })
                .collect(Collectors.toList());
        return references;
    }

    public List<ReferenceDto> convertFromStuff(){return null;}

    public List<ReferenceDto> convertFromUsers(List<User> users) {
        List<ReferenceDto> references = users.stream()
                .map(user -> {
                    ReferenceDto ref = new ReferenceDto();
                    ref.setId(user.getId());
                    //ref.setType(user.getType);
                    ref.setPhone(user.getPhone());
                    ref.setName(user.getName());
                    ref.setEmail(user.getEmail());
                    return ref;
                })
                .collect(Collectors.toList());
        return references;
    }
}
