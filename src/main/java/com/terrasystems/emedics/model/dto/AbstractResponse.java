package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public abstract class AbstractResponse implements Serializable {
    private StateDto state;

    public StateDto getState() {
        return state;
    }

    public void setState(StateDto state) {
        this.state = state;
    }
}
