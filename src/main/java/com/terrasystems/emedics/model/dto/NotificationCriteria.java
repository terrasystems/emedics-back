package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class NotificationCriteria implements Serializable {
    private static final long serialVersionUID = 6701041581013077276L;

    private int period;
    private String fromName;
    private String description;
    private String templateId;
    private Integer formType;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }
}
