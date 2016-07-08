package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class StuffCriteria implements Serializable {
    private static final long serialVersionUID = 9096911755528487682L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
