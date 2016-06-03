package com.terrasystems.emedics.model.dto;

import java.io.Serializable;

public class DashboardTemplateResponse implements Serializable {
    private static final long serialVersionUID = -3665023562103861683L;
    private StateDto state;
    private Object result;
    public DashboardTemplateResponse() {
    }
    public StateDto getState() {
        return state;
    }
    public void setState(StateDto state) {
        this.state = state;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
}