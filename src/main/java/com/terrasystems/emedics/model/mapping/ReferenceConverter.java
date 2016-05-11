package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.Reference;
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
                    return ref;
                })
                .collect(Collectors.toList());
        return references;
    }

    public List<ReferenceDto> convertFromStuff(){return null;}
}
