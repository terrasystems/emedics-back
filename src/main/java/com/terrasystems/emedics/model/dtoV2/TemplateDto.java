package com.terrasystems.emedics.model.dtoV2;


import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class TemplateDto implements Serializable {

    private static final long serialVersionUID = -8991369486552245506L;
    private String id;
    private JsonNode body;
    private String type;
    private String name;
    private Boolean commerce;
    private Boolean isPaid;
    private String number;
    private Boolean load;
    private String descr;

    public TemplateDto() {
    }

    public TemplateDto(String id, JsonNode body, String type, String name, Boolean commerce, Boolean isPaid, String number, Boolean load, String descr) {
        this.id = id;
        this.body = body;
        this.type = type;
        this.name = name;
        this.commerce = commerce;
        this.isPaid = isPaid;
        this.number = number;
        this.load = load;
        this.descr = descr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JsonNode getBody() {
        return body;
    }

    public void setBody(JsonNode body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCommerce() {
        return commerce;
    }

    public void setCommerce(Boolean commerce) {
        this.commerce = commerce;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getLoad() {
        return load;
    }

    public void setLoad(Boolean load) {
        this.load = load;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("body", body)
                .append("type", type)
                .append("name", name)
                .append("commerce", commerce)
                .append("isPaid", isPaid)
                .append("number", number)
                .append("load", load)
                .append("descr", descr)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TemplateDto that = (TemplateDto) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(type, that.type)
                .append(name, that.name)
                .append(commerce, that.commerce)
                .append(isPaid, that.isPaid)
                .append(number, that.number)
                .append(load, that.load)
                .append(descr, that.descr)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(type)
                .append(name)
                .append(commerce)
                .append(isPaid)
                .append(number)
                .append(load)
                .append(descr)
                .toHashCode();
    }
}