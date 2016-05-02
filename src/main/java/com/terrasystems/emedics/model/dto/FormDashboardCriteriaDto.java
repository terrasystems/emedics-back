package com.terrasystems.emedics.model.dto;


import java.io.Serializable;
import java.util.Set;

public class FormDashboardCriteriaDto implements Serializable {
    private static final long serialVersionUID = -5996261411972549759L;
    private Set<String> list;

    public FormDashboardCriteriaDto() {
    }

    public FormDashboardCriteriaDto(Set<String> list) {
        this.list = list;
    }

    public Set<String> getList() {
        return list;
    }

    public void setList(Set<String> list) {
        this.list = list;
    }
}
