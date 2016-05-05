package com.terrasystems.emedics.model.dto;


public class DashboardNotificationResponse extends AbstractResponse{
    private static final long serialVersionUID = 6860534434142859685L;

    private Object result;
    private StateDto state;

    public DashboardNotificationResponse() {
    }

    public DashboardNotificationResponse(Object result, StateDto state) {
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
