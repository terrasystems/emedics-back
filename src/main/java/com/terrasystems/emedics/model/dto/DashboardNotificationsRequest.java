package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class DashboardNotificationsRequest implements Serializable {
    private static final long serialVersionUID = -6840110662933033810L;

    private NotificationDto notification;
    private NotificationsDashboardCriteriaDto criteria;
    private PageDto pageDto;

    public DashboardNotificationsRequest() {
    }

    public DashboardNotificationsRequest(NotificationsDashboardCriteriaDto criteria, PageDto pageDto) {
        this.criteria = criteria;
        this.pageDto = pageDto;
    }

    public NotificationDto getNotification() {
        return notification;
    }

    public void setNotification(NotificationDto notification) {
        this.notification = notification;
    }

    public NotificationsDashboardCriteriaDto getCriteria() {
        return criteria;
    }

    public void setCriteria(NotificationsDashboardCriteriaDto criteria) {
        this.criteria = criteria;
    }

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
