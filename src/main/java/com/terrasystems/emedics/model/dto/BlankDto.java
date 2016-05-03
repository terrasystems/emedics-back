package com.terrasystems.emedics.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.sun.istack.internal.Nullable;
import com.terrasystems.emedics.model.Form;

import java.io.Serializable;
import java.util.List;

public class BlankDto implements Serializable{
    private static final long serialVersionUID = -8721461755556495891L;
    private String id;
    private String type;
    private String category;

    private String body;
    private String descr;
    private List<Form> forms;

    public BlankDto() {
    }

    public BlankDto(String id, String type, String category, String body, String descr, List<Form> forms) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.body = body;
        this.descr = descr;
        this.forms = forms;
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

    @JsonIgnore
    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("category", category)
                .add("body", body)
                .add("descr", descr)
                .add("forms", forms)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlankDto)) return false;

        BlankDto blankDto = (BlankDto) o;

        return Objects.equal(id, blankDto.id)&&
                Objects.equal(type, blankDto.type)&&
                Objects.equal(category, blankDto.category)&&
                Objects.equal(body, blankDto.body)&&
                Objects.equal(descr, blankDto.descr)&&
                Objects.equal(forms, blankDto.forms);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, type, category, body, descr, forms);
    }
}
