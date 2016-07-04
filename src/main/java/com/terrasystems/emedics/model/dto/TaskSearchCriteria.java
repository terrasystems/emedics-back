package com.terrasystems.emedics.model.dto;


import javax.annotation.Nullable;
import java.io.Serializable;

public class TaskSearchCriteria implements Serializable{
    private static final long serialVersionUID = 7117761988440805625L;
    private int period;
    private String templateName;
    @Nullable
    private String patientName;
    private String fromName;

    public TaskSearchCriteria(int period, String templateName, String patientName, String fromName) {
        this.period = period;
        this.templateName = templateName;
        this.patientName = patientName;
        this.fromName = fromName;
    }
     public TaskSearchCriteria() {}

    public int getPeriod() {
        return period;
    }


    public void setPeriod(int period) {
        this.period = period;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }
}
