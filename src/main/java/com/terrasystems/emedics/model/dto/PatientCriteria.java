package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class PatientCriteria implements Serializable {
    private static final long serialVersionUID = -5808470678061041129L;
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
