package com.terrasystems.emedics.model.dto;

import java.util.List;

public class ListDashboardFormsResponse extends AbstractResponse {
    private static final long serialVersionUID = 1161368771589456067L;

    private List<FormDto> list;
    private StateDto state;

    public ListDashboardFormsResponse() {}

    public ListDashboardFormsResponse(List<FormDto> list, StateDto state) {
        this.list = list;
        this.state = state;
    }

    public List<FormDto> getList() {
        return list;
    }

    public void setList(List<FormDto> list) {
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
