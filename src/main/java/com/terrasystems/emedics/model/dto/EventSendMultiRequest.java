package com.terrasystems.emedics.model.dto;

import java.io.Serializable;
import java.util.List;


public class EventSendMultiRequest implements Serializable{
    private static final long serialVersionUID = 1047652792721299215L;
    private List<String> toUsers;
    private String event;
    private String message;
    private String patient;

    public List<String> getToUsers() {
        return toUsers;
    }

    public void setToUsers(List<String> toUsers) {
        this.toUsers = toUsers;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
}
