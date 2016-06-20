package com.terrasystems.emedics.model.dto;


import com.google.common.base.Objects;
import com.terrasystems.emedics.enums.DocTypeEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class DocTypeDto implements Serializable {
    private static final long serialVersionUID = 6671832982180039706L;
    private String id;
    private String name;
    private DocTypeEnum value;

    public DocTypeDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocTypeEnum getValue() {
        return value;
    }

    public void setValue(DocTypeEnum value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DocTypeDto that = (DocTypeDto) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("value", value)
                .toString();
    }
}
