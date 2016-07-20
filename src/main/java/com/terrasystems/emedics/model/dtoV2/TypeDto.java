package com.terrasystems.emedics.model.dtoV2;


import com.terrasystems.emedics.enums.UserType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class TypeDto implements Serializable {

    private static final long serialVersionUID = -283311616572663325L;
    private String id;
     private UserType userType;
     private String name;

    public TypeDto() {
    }

    public TypeDto(String id, UserType userType, String name) {
        this.id = id;
        this.userType = userType;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userType", userType)
                .append("name", name)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TypeDto typesDto = (TypeDto) o;

        return new EqualsBuilder()
                .append(id, typesDto.id)
                .append(name, typesDto.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .toHashCode();
    }
}
