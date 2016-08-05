package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;

public interface NotificationService {
    ResponseDto allNotifications(CriteriaDto dto);
    ResponseDto acceptNotification(String id);
    ResponseDto declineNotification(String id);
}
