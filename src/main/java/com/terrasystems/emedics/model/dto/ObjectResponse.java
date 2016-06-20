package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class ObjectResponse implements Serializable {
    private static final long serialVersionUID = 5258243923443172225L;
    private StateDto state;
    private Object result;

    public ObjectResponse() {
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
