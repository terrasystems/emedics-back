package com.terrasystems.emedics.model.dto;

import java.io.Serializable;


public class DashboardPatientsResponse implements Serializable {
    private static final long serialVersionUID = 4677855712775369822L;

    private StateDto state;
    private Object result;

    public DashboardPatientsResponse() {}

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
