package com.terrasystems.emedics.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


public class DashboardFormsRequest implements Serializable {
    private static final long serialVersionUID = 7948074866237898872L;

    private Set<Long> forms;

    public DashboardFormsRequest(){}

    public DashboardFormsRequest(Set<Long> forms) {
        this.forms = forms;
    }

    public Set<Long> getForms() {
        return forms;
    }

    public void setForms(Set<Long> forms) {
        this.forms = forms;
    }
}
