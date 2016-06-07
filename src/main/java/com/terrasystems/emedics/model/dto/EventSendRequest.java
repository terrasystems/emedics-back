package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class EventSendRequest implements Serializable {
    private static final long serialVersionUID = -697920223965574405L;
    private String toUser;
    private String event;
    private String message;

    public EventSendRequest() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
