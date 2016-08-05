package com.terrasystems.emedics.model.dtoV2;


import com.terrasystems.emedics.enums.StatusEnum;

import java.io.Serializable;

public class TaskCriteriaDto implements Serializable {

    private int period;
    private String templateName;
    private String patientName;
    private String fromName;
    private StatusEnum status;

    public TaskCriteriaDto() {
    }

    public TaskCriteriaDto(int period, String templateName, String patientName, String fromName, StatusEnum status) {
        this.period = period;
        this.templateName = templateName;
        this.patientName = patientName;
        this.fromName = fromName;
        this.status = status;
    }

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

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
