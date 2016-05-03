package com.terrasystems.emedics.model.dto;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class FormDto implements Serializable {

    private static final long serialVersionUID = -4734233423443392661L;
    private String id;
    private String type;
    private String category;
    private String body;
    private String descr;
    private String name;
    private String number;
    private String data;
    private boolean active;

    public FormDto() {
    }

    public FormDto(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public FormDto(String id, String type, String category, String body, String descr, boolean active) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.body = body;
        this.descr = descr;
        this.active = active;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNumber() {
        return number;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("category", category)
                .add("body", body)
                .add("descr", descr)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormDto formDto = (FormDto) o;
        return  Objects.equal(id, formDto.id) &&
                Objects.equal(type, formDto.type) &&
                Objects.equal(category, formDto.category)&&
                Objects.equal(body, formDto.body)&&
                Objects.equal(descr, formDto.descr);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, type, category, body, descr);
    }
}
