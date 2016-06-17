package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.*;
import com.terrasystems.emedics.model.dto.ReferenceDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferenceConverter {

    public List<ReferenceDto> convertFromDoctors(List<Doctor> doctors) {
        List<ReferenceDto> references = doctors.stream()
                .map(doctor -> {
                    ReferenceDto ref = new ReferenceDto();
                    ref.setId(doctor.getId());
                    if (doctor.getType() == null) {
                        ref.setType(null);
                    } else {
                        ref.setType(doctor.getType().getName());
                    }
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

    public List<ReferenceDto> convertFromUsers(Set<User> users) {
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
    public List<ReferenceDto> convertFromPatients(List<Patient> patients) {
        List<ReferenceDto> refs = patients.stream()
                .map(patient -> {
                    ReferenceDto ref = new ReferenceDto();
                    ref.setId(patient.getId());
                    ref.setEmail(patient.getEmail());
                    ref.setPhone(patient.getPhone());
                    ref.setName(patient.getName());
                    return ref;
                })
                .collect(Collectors.toList());
        return refs;
    }
}
