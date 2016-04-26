package com.terrasystems.emedics.model.dto;

import com.terrasystems.emedics.model.Form;

import java.util.List;

public class ListDashboardFormsResponse extends AbstractResponse {
    private static final long serialVersionUID = 1161368771589456067L;
    private List<Form> list;
    private StateDto state;

    public ListDashboardFormsResponse() {}

    public ListDashboardFormsResponse(List<Form> list, StateDto state) {
        this.list = list;
        this.state = state;
    }

    public List<Form> getList() {
        return list;
    }

    public void setList(List<Form> list) {
        this.list = list;
    }

    @Override
    public StateDto getState() {
        return state;
    }

    @Override
    public void setState(StateDto state) {
        this.state = state;
    }


}
