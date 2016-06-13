package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class DashboardPatientEventResponse implements Serializable {
    private static final long serialVersionUID = -9179413710964381685L;
    private StateDto state;
    private Object result;

    public DashboardPatientEventResponse() {}

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