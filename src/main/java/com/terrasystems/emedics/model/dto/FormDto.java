package com.terrasystems.emedics.model.dto;


import com.google.common.base.MoreObjects;

import java.io.Serializable;

public class FormDto implements Serializable {

    private static final long serialVersionUID = -4734233423443392661L;
    private String id;
    private BlankDto blank;
    private String data;
    private boolean active;

    public FormDto() {
    }

    public FormDto(String id, String data, BlankDto blank) {
        this.id = id;
        this.data = data;
        this.blank = blank;
    }

    public FormDto(String id, BlankDto blank, String data, boolean active) {
        this.id = id;

        this.active = active;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
