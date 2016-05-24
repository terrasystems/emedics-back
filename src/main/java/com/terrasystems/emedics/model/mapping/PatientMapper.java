package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Patient;
import com.terrasystems.emedics.model.UserForm;
import com.terrasystems.emedics.model.dto.PatientDto;
import com.terrasystems.emedics.model.dto.UserFormDto;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        FormMapper mapper = FormMapper.getInstance();
        PatientDto dto = new PatientDto();
        List<UserForm> forms = entity.getUserForms();
        List<UserFormDto> formDtos = forms.stream()
                .map(userForm -> {
                    UserFormDto form = new UserFormDto();
                    try {
                        form = mapper.toDto(userForm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return form;
                })
                .collect(Collectors.toList());
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        //dto.setForms(formDtos);
        dto.setAllowedFormsCount(entity.getAllowedFormsCount());

        return dto;
    }

    public Patient toEntity(PatientDto dto) {
        Patient entity = new Patient();
        entity.setEmail(dto.getEmail());
        entity.setAllowedFormsCount(dto.getAllowedFormsCount());
        return entity;
    }


}
