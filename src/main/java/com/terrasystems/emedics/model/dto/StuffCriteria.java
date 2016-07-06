package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class StuffCriteria implements Serializable {
    private static final long serialVersionUID = -1172811765105400033L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
