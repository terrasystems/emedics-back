package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class EventCreateRequest implements Serializable{
    private static final long serialVersionUID = -5766352564377136791L;


    private UserTemplateDto template;
    private String patient;

    public EventCreateRequest() {}

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public UserTemplateDto getTemplate() {
        return template;
    }

    public void setTemplate(UserTemplateDto template) {
        this.template = template;
    }
}
