package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Doctor;
import com.terrasystems.emedics.model.dto.DoctorDto;

public final class DoctorMapper {

    public static DoctorDto toDto(Doctor entity) {
        DoctorDto dto = new DoctorDto();
        dto.setEmail(entity.getEmail());
        dto.setType(entity.getDiscriminatorValue()); //without test
        dto.setUsername(entity.getUsername());  //without test
        dto.setPassword(entity.getPassword());
        dto.setClinic(entity.getClinic());
        return dto;
    }

    public static Doctor toEntity(DoctorDto dto) {
        Doctor entity = new Doctor();
        entity.setEmail(dto.getEmail());
        //entity.setDiscriminatorValue(dto.getType());  //methods dto.getType(), setDiscriminatorValue() not supported yet
        //entity.setUsername(dto.getUsername());    //method getUsername() not supported yet
        entity.setPassword(dto.getPassword());
        entity.setClinic(dto.getClinic());
        return entity;
    }
}
