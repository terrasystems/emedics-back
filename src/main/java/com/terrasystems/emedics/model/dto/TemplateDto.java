package com.terrasystems.emedics.model.dto;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Objects;
import com.terrasystems.emedics.enums.CommercialEnum;
import com.terrasystems.emedics.enums.TypeEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    private CommercialEnum commercialEnum;
    private TypeEnum typeEnum;
    private boolean existPaid;

    public TemplateDto() {
    }

    public TemplateDto(String id, String type, String category, String name, JsonNode body, String descr, String number,
                       CommercialEnum commercialEnum, TypeEnum typeEnum, boolean existPaid) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.name = name;
        this.body = body;
        this.descr = descr;
        this.number = number;
        this.commercialEnum = commercialEnum;
        this.typeEnum = typeEnum;
        this.existPaid = existPaid;
    }

    public boolean isExistPaid() {
        return existPaid;
    }

    public void setExistPaid(boolean existPaid) {
        this.existPaid = existPaid;
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

    public CommercialEnum getCommercialEnum() {
        return commercialEnum;
    }

    public void setCommercialEnum(CommercialEnum commercialEnum) {
        this.commercialEnum = commercialEnum;
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
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
                .add("commercialEnum", commercialEnum)
                .add("typeEnum", typeEnum)
                .add("existPaid", existPaid)
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
                .append(category, that.category)
                .append(name, that.name)
                .append(body, that.body)
                .append(descr, that.descr)
                .append(number, that.number)
                .append(commercialEnum, that.commercialEnum)
                .append(typeEnum, that.typeEnum)
                .append(existPaid, that.existPaid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(type)
                .append(category)
                .append(name)
                .append(body)
                .append(descr)
                .append(number)
                .append(commercialEnum)
                .append(typeEnum)
                .append(existPaid)
                .toHashCode();
    }
}
