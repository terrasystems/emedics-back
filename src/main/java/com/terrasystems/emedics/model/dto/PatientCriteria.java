package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class PatientCriteria implements Serializable {
    private static final long serialVersionUID = 4149339956348479450L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
