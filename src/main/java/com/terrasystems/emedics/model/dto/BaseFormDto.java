package com.terrasystems.emedics.model.dto;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class BaseFormDto implements Serializable{
    private static final long serialVersionUID = 1719883551509206698L;

    private String id;
    private BlankDto blank;
    private JsonNode data;

    public BaseFormDto() {
    }

    public BaseFormDto(String id, BlankDto blank, JsonNode data) {
        this.id = id;
        this.blank = blank;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BlankDto getBlank() {
        return blank;
    }

    public void setBlank(BlankDto blank) {
        this.blank = blank;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseFormDto)) return false;
        BaseFormDto that = (BaseFormDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(blank, that.blank) &&
                Objects.equal(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, blank, data);
    }
}
