package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class ReferenceCriteria implements Serializable{
    private static final long serialVersionUID = -6866869778107237994L;
    private String name;
    private int type;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
