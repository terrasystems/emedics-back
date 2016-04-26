package com.terrasystems.emedics.model.dto;


import com.terrasystems.emedics.model.Form;

import java.io.Serializable;
import java.util.Set;

public class FormDashboardCriteriaDto implements Serializable {
    private static final long serialVersionUID = -5996261411972549759L;
    private Set<Long> forms;

    public FormDashboardCriteriaDto() {
    }

    public FormDashboardCriteriaDto(Set<Long> forms) {
        this.forms = forms;
    }

    public Set<Long> getForms() {
        return forms;
    }

    public void setForms(Set<Long> forms) {
        this.forms = forms;
    }
}
