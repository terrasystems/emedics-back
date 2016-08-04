package com.terrasystems.emedics.model.mapping;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dtoV2.TaskDto;

import java.io.IOException;

public class TaskMapper {
    private static TaskMapper mapper;
    private ObjectMapper objectMapper;

    public static TaskMapper getInstance() {
        if (mapper == null) {
            mapper = new TaskMapper();
            return mapper;
        }
        return mapper;
    }

    public TaskDto toDto(Event entity) throws IOException {
        objectMapper = new ObjectMapper();
        UserMapper userMapper = UserMapper.getInstance();
        TemplateMapper templateMapper = TemplateMapper.getInstance();
        TaskDto dto = new TaskDto();
        dto.setId(entity.getId());
        dto.setFromUser(userMapper.toDTO(entity.getFromUser()));
        if (entity.getToUser() != null) {
            dto.setToUser(userMapper.toDTO(entity.getToUser()));
        }
        dto.setDescr(entity.getDescr());
        dto.setTemplateDto(templateMapper.toDto(entity.getTemplate()));
        dto.setPatient(userMapper.toDTO(entity.getPatient()));
        dto.setDate(entity.getDate());
        dto.setStatus(entity.getStatus());
        dto.setModel(objectMapper.readTree(entity.getData()));
        return dto;
    }
}
