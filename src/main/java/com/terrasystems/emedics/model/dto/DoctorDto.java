package com.terrasystems.emedics.model.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;


public class DoctorDto implements Serializable {
    private static final long serialVersionUID = 1509556509931292216L;
    private String type;
    private String email;

    private String username;
    private String password;
    private String clinic;
    private String org;

    public DoctorDto() {
    }

    public DoctorDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public DoctorDto(String type, String email, String username, String password, String clinic) {
        this.type = type;
        this.email = email;
        this.username = username;
        this.password = password;
        this.clinic = clinic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .add("password", password)
                .add("username", username)
                .add("type", type)
                .add("clinic", clinic)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoctorDto doctorDto = (DoctorDto) o;
        return  Objects.equal(email, doctorDto.email) &&
                Objects.equal(password, doctorDto.password) &&
                Objects.equal(username, doctorDto.username)&&
                Objects.equal(clinic, doctorDto.clinic) &&
                Objects.equal(org, doctorDto.org);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email, password, username, clinic, org);
    }
}
