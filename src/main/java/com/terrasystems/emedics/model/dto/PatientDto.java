package com.terrasystems.emedics.model.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;


public class PatientDto implements Serializable {
    private static final long serialVersionUID = -8311646841406921946L;
    private String type;
    private String email;
    private String password;
    private String username;
    private int allowedFormsCount;

    public PatientDto() {
    }

    public PatientDto(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public PatientDto(String type, String email, String password, String username, int allowedFormsCount) {
        this.type = type;
        this.email = email;
        this.password = password;
        this.username = username;
        this.allowedFormsCount = allowedFormsCount;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAllowedFormsCount() {
        return allowedFormsCount;
    }

    public void setAllowedFormsCount(int allowedFormsCount) {
        this.allowedFormsCount = allowedFormsCount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("email", email)
                .add("password", password)
                .add("username", username)
                .add("allowedFormsCount", allowedFormsCount)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientDto)) return false;

        PatientDto patientDto = (PatientDto) o;
        return  Objects.equal(email, patientDto.email) &&
                Objects.equal(password, patientDto.password) &&
                Objects.equal(username, patientDto.username)&&
                Objects.equal(allowedFormsCount, patientDto.allowedFormsCount);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email, password, username, allowedFormsCount);
    }
}
