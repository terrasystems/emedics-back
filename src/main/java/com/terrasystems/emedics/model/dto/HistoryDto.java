package com.terrasystems.emedics.model.dto;


import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;
import java.util.Date;

public class HistoryDto implements Serializable {
    private static final long serialVersionUID = 3408380724323157277L;

    private String id;
    private JsonNode data;
    private UserFormDto form;
    private Date date;

    public HistoryDto() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public UserFormDto getForm() {
        return form;
    }

    public void setForm(UserFormDto form) {
        this.form = form;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
