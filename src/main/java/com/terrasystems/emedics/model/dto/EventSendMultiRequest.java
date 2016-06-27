package com.terrasystems.emedics.model.dto;

import java.io.Serializable;
import java.util.List;


public class EventSendMultiRequest implements Serializable{
    private static final long serialVersionUID = 1047652792721299215L;
    private String template;
    private String message;
    private List<String> patients;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getPatients() {
        return patients;
    }

    public void setPatients(List<String> patients) {
        this.patients = patients;
    }
}
