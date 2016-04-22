package com.terrasystems.emedics.model.dto;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class OrganisationDto implements Serializable {
    private static final long serialVersionUID = -2579820149125543492L;
    private String name;
    private String fullname;
    private String website;
    private String address;

    public OrganisationDto(){}

    public OrganisationDto(String name, String fullname, String website, String address) {
        this.name = name;
        this.fullname = fullname;
        this.website = website;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("fullname", fullname)
                .add("website", website)
                .add("address", address)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganisationDto that = (OrganisationDto) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(fullname, that.fullname) &&
                Objects.equal(website, that.website) &&
                Objects.equal(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, fullname, website, address);
    }
}
