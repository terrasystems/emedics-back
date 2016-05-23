package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class SharedFormDto implements Serializable {
    private static final long serialVersionUID = -4054028237774172005L;
    private String Id;
    private String data;
    private BlankDto blank;

    public SharedFormDto() {}

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BlankDto getBlank() {
        return blank;
    }

    public void setBlank(BlankDto blank) {
        this.blank = blank;
    }


}
