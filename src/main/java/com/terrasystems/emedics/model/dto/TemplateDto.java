package com.terrasystems.emedics.model.dto;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Objects;

import java.io.Serializable;

public class TemplateDto implements Serializable {
    private static final long serialVersionUID = -8899149929266062705L;
    private String id;
    private String type;
    private String category;
    private String name;
    private JsonNode body;
    private String descr;
    private String number;

    public TemplateDto() {
    }

    public TemplateDto(String id, String type, String category, String name, JsonNode body, String descr, String number) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.name = name;
        this.body = body;
        this.descr = descr;
        this.number = number;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonNode getBody() {
        return body;
    }

    public void setBody(JsonNode body) {
        this.body = body;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("category", category)
                .add("name", name)
                .add("body", body)
                .add("descr", descr)
                .add("number", number)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TemplateDto)) return false;
        TemplateDto that = (TemplateDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(type, that.type) &&
                Objects.equal(category, that.category) &&
                Objects.equal(name, that.name) &&
                Objects.equal(body, that.body) &&
                Objects.equal(descr, that.descr) &&
                Objects.equal(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, type, category, name, body, descr, number);
    }
}
