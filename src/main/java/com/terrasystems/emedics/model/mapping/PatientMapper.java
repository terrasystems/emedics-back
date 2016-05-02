package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.PatientDto;

public class PatientMapper {

    public static PatientDto toDto(Patient entity) {
        PatientDto dto = new PatientDto();
        dto.setEmail(entity.getEmail());
        dto.setType(entity.getDiscriminatorValue()); //without test
        dto.setUsername(entity.getUsername());  //without test
        dto.setPassword(entity.getPassword());
        dto.setAllowedFormsCount(entity.getAllowedFormsCount());
        return dto;
    }

    public static Patient toEntity(PatientDto dto) {
        Patient entity = new Patient();
        entity.setEmail(dto.getEmail());
        //entity.setDiscriminatorValue(dto.getType());  //methods dto.getType(), setDiscriminatorValue() not supported yet
        //entity.setUsername(dto.getUsername());    //method getUsername() not supported yet
        entity.setPassword(dto.getPassword());
        entity.setAllowedFormsCount(dto.getAllowedFormsCount());
        return entity;
    }


}
