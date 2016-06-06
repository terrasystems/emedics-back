package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class DashboardTaskResponse implements Serializable {
    private static final long serialVersionUID = -6892305772656775607L;

    private StateDto state;
    private Object result;

    public DashboardTaskResponse() {}

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
