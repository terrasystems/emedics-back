package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class TaskSearchCriteria implements Serializable{
    private static final long serialVersionUID = 7117761988440805625L;
    private int period;
    private String templateId;
    private String patientId;
    private String fromId;

    public int getPeriod() {
        return period;
    }



    public void setPeriod(int period) {
        this.period = period;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
}
