package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class MyRefsRequest implements Serializable {
    private static final long serialVersionUID = -4576725267982391358L;
    private String search;
    private String type;

    public MyRefsRequest() {}

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
