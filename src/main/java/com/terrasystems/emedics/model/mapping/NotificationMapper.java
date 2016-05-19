package com.terrasystems.emedics.model.mapping;


import com.terrasystems.emedics.model.Notification;
import com.terrasystems.emedics.model.dto.NotificationDto;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NotificationMapper {
    private  static NotificationMapper mapper;

    public static NotificationMapper getInstance() {
        if (mapper == null) {
            mapper = new NotificationMapper();
            return mapper;
        }
        return mapper;
    }

    public NotificationMapper() {}

    Function<Notification, NotificationDto> toDto = (notification) -> {
        NotificationDto dto = new NotificationDto();
        UserMapper userMapper = UserMapper.getInstance();
        dto.setType(notification.getType());
        dto.setId(notification.getId());
        dto.setDate(notification.getDate());
        dto.setFromUser(userMapper.toDTO(notification.getFromUser()));
        dto.setToUser(userMapper.toDTO(notification.getToUser()));
        dto.setReadtype(notification.getReadtype());
        dto.setUserForm(notification.getUserForm().getId());
        return dto;
    };

    Function<NotificationDto,Notification> fromDto = (notificationDto) -> {
        Notification notification = new Notification();
        notification.setReadtype(notificationDto.getReadtype());
        notification.setType(notificationDto.getType());
        notification.setDate(new Date());
        notification.setText(notificationDto.getText());
        notification.setTitle(notificationDto.getTitle());
        return notification;
    };


    public List<NotificationDto> fromNotifications(List<Notification> notifications) {
        List<NotificationDto> dtos;
        dtos = notifications.stream()
                .map(toDto)
                .collect(Collectors.toList());
        return dtos;
    }

    public NotificationDto fromNotification(Notification notification) {
        return (NotificationDto) toDto;
    }

    public Notification fromDto(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setReadtype(dto.getReadtype());
        notification.setType(dto.getType());
        notification.setDate(new Date());
        notification.setText(dto.getText());
        notification.setTitle(dto.getTitle());
        return notification;
    }

}
