package com.terrasystems.emedics.model.dtoV2;


import java.io.Serializable;

public class ResponseDto implements Serializable {

    private static final long serialVersionUID = -3804351716807995030L;
    private boolean state = true;
    private String msg;
    private Object result;

    public ResponseDto() {
    }

    public ResponseDto(Boolean state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public ResponseDto(boolean state, String msg, Object result) {
        this.state = state;
        this.msg = msg;
        this.result = result;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
