package com.terrasystems.emedics.model.dto;


public class DashboardReferenceResponse extends AbstractResponse{
    private static final long serialVersionUID = -4875782160740599882L;

    private Object resualt;
    private StateDto state;

    public DashboardReferenceResponse() {
    }

    public DashboardReferenceResponse(Object resualt, StateDto state) {
        this.resualt = resualt;
        this.state = state;
    }

    public Object getObject() {
        return resualt;
    }

    public void setObject(Object resualt) {
        this.resualt = resualt;
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


