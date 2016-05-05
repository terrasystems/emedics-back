package com.terrasystems.emedics.model.dto;


public class DashboardReferenceResponse extends AbstractResponse{
    private static final long serialVersionUID = -4875782160740599882L;

    private Object result;
    private StateDto state;

    public DashboardReferenceResponse() {
    }

    public DashboardReferenceResponse(Object result, StateDto state) {
        this.result = result;
        this.state = state;
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


