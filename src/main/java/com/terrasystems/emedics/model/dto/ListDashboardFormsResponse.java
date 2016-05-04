package com.terrasystems.emedics.model.dto;

import java.util.List;

public class ListDashboardFormsResponse extends AbstractResponse {
    private static final long serialVersionUID = 1161368771589456067L;

    private Object result;
    private PageDto page;
    private StateDto state;

    public ListDashboardFormsResponse() {}

    public ListDashboardFormsResponse(Object result, StateDto state, PageDto page) {
        this.result = result;
        this.state = state;
        this.page = page;
    }

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
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
