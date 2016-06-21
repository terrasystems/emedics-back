package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.dto.PatientDto;

public class PatientMapper {
    private static PatientMapper mapper;

    public static PatientMapper getInstance() {
        if (mapper == null) {
            mapper = new PatientMapper();
            return mapper;
        }
        return mapper;
    }

    public PatientDto toDto(Patient entity) {
        PatientDto dto = new PatientDto();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setAllowedFormsCount(entity.getAllowedFormsCount());
        dto.setEnabled(entity.isEnabled());
        return dto;
    }

    public Patient toEntity(PatientDto dto) {
        Patient entity = new Patient();
        entity.setEmail(dto.getEmail());
        entity.setAllowedFormsCount(dto.getAllowedFormsCount());
        return entity;
    }


}
