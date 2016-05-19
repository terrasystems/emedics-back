package com.terrasystems.emedics.model.dto;


public class ExceptionHandlerResponse extends AbstractResponse {
    private static final long serialVersionUID = -1721663025163276088L;

    private StateDto state;

    public ExceptionHandlerResponse() {
    }

    public ExceptionHandlerResponse(StateDto state) {
        this.state = state;
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
