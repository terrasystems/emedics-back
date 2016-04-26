package com.terrasystems.emedics.model.dto;

import com.terrasystems.emedics.model.Form;

import java.util.List;

public class ListDashboardFormsResponse extends AbstractResponse {
    private static final long serialVersionUID = 1161368771589456067L;
    private List<Form> list;

    public ListDashboardFormsResponse(List<Form> list) {
        this.list = list;
    }

    public List<Form> getForms() {
        return list;
    }

    public void setForms(List<Form> forms) {
        this.list = forms;
    }
}
