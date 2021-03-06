package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Reference;
import com.terrasystems.emedics.model.Stuff;
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

    public List<ReferenceDto> convertFromStuff(List<Stuff> stuffList){
        List<ReferenceDto> references = stuffList.stream()
                .map(stuff -> {
                    ReferenceDto ref = new ReferenceDto();
                    ref.setId(stuff.getId());
                    ref.setName(stuff.getOrganization().getName());
                    ref.setEmail(stuff.getEmail());
                    ref.setPhone(stuff.getPhone());
                    ref.setType(stuff.getOrganization().getType());
                    return ref;
                })
                .collect(Collectors.toList());

        return references;
    }

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
