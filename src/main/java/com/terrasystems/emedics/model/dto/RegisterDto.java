package com.terrasystems.emedics.model.dto;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class RegisterDto implements Serializable {
    private static final long serialVersionUID = 7115960048654409267L;
    private String type;
    private UserDto user;
    private OrganisationDto organisation;

    public RegisterDto(){}


    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OrganisationDto getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationDto organisation) {
        this.organisation = organisation;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("user", user)
                .add("organisation", organisation)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterDto that = (RegisterDto) o;
        return Objects.equal(type, that.type) &&
                Objects.equal(user, that.user) &&
                Objects.equal(organisation, that.organisation);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, user, organisation);
    }
}
