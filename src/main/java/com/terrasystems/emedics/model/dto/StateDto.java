package com.terrasystems.emedics.model.dto;

import java.io.Serializable;


public class StateDto implements Serializable {
    private static final long serialVersionUID = -3131470666601121094L;
    private boolean value;
    private String message;

    public StateDto(boolean value, String message) {
        this.value = value;
        this.message = message;
        System.out.println("StateDto.msg = " + this.message);
    }
    public StateDto() {}

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
