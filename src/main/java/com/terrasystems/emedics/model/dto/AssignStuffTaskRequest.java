package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class AssignStuffTaskRequest implements Serializable {
    private static final long serialVersionUID = 4671095448574628603L;
    private String stuffId;
    private String eventId;

    public String getStuffId() {
        return stuffId;
    }

    public void setStuffId(String stuffId) {
        this.stuffId = stuffId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
}
