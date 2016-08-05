package com.terrasystems.emedics.model.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.dtoV2.NotificationDto;

public class NotificationMapper {

    private static NotificationMapper mapper;
    private ObjectMapper objectMapper;

    public static NotificationMapper getInstance() {
        if (mapper == null) {
            mapper = new NotificationMapper();
            return mapper;
        }
        return mapper;
    }

    public NotificationDto toDto(Event entity) {
        NotificationDto dto = new NotificationDto();
        dto.setDate(entity.getDate());
        dto.setDescr(entity.getDescr());
        dto.setId(entity.getId());
        dto.setFromUserName(entity.getFromUser().getName());
        dto.setTemplateName(entity.getTemplate().getName());
        return dto;
    }
}
