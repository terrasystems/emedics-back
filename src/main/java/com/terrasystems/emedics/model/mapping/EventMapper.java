package com.terrasystems.emedics.model.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dto.EventDto;

import java.io.IOException;

public class EventMapper {
    private ObjectMapper objectMapper;
    private static EventMapper mapper;

    public static EventMapper getInstance() {
        if (mapper == null) {
            mapper = new EventMapper();
            return mapper;
        }
        return mapper;
    }

    public EventMapper() {
        objectMapper = new ObjectMapper();
    }

    public EventDto toDto(Event entity) throws IOException {
        UserMapper userMapper = UserMapper.getInstance();
        TemplateMapper templateMapper = TemplateMapper.getInstance();
        EventDto dto = new EventDto();
        dto.setDate(entity.getDate());
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus().name());
        dto.setData(objectMapper.readTree(entity.getData()));
        if (entity.getToUser() == null) {
            dto.setToUser(null);
        } else {
            dto.setToUser(userMapper.toDTO(entity.getToUser()));
        }
        if (entity.getFromUser() == null){
            dto.setFromUser(null);
        } else {
            dto.setFromUser(userMapper.toDTO(entity.getFromUser()));
        }
        if (entity.getPatient() == null) {
            dto.setPatient(null);
        } else {
            dto.setPatient(userMapper.toDTO(entity.getPatient()));
        }
        dto.setTemplate(templateMapper.toDto(entity.getTemplate()));
        dto.setDescr(entity.getDescr());
        return dto;
    }
}
