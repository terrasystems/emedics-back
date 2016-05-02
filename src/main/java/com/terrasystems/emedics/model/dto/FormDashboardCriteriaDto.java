package com.terrasystems.emedics.model.dto;


import java.io.Serializable;
import java.util.Set;

public class FormDashboardCriteriaDto implements Serializable {
    private static final long serialVersionUID = -5996261411972549759L;
    private Set<String> forms;

    public FormDashboardCriteriaDto() {
    }

    public FormDashboardCriteriaDto(Set<String> forms) {
        this.forms = forms;
    }

    public Set<String> getForms() {
        return forms;
    }

    public void setForms(Set<String> forms) {
        this.forms = forms;
    }
}
