package com.terrasystems.emedics.model.dto;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.MoreObjects;

import java.io.Serializable;

public class UserFormDto implements Serializable {

    private static final long serialVersionUID = -4734233423443392661L;
    private String id;
    private BlankDto blank;
    private JsonNode data;
    private boolean active;

    public UserFormDto() {
    }

    public UserFormDto(String id, JsonNode data, BlankDto blank) {
        this.id = id;
        this.data = data;
        this.blank = blank;
    }

    public UserFormDto(String id, BlankDto blank, String data, boolean active) {
        this.id = id;
        this.blank = blank;
        this.active = active;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BlankDto getBlank() {
        return blank;
    }

    public void setBlank(BlankDto blank) {
        this.blank = blank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }





    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
