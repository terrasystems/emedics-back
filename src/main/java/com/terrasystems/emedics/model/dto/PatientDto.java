package com.terrasystems.emedics.model.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;


public class PatientDto implements Serializable {
    private static final long serialVersionUID = -8311646841406921946L;
    private String id;
    private String email;
    private String phone;
    private String name;
    //private List<UserFormDto> forms;
    private List<HistoryDto> history;
    private int allowedFormsCount;

    public PatientDto() {
    }

    public PatientDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public List<HistoryDto> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryDto> history) {
        this.history = history;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

 /*   public List<UserFormDto> getForms() {
        return forms;
    }

    public void setForms(List<UserFormDto> forms) {
        this.forms = forms;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAllowedFormsCount() {
        return allowedFormsCount;
    }

    public void setAllowedFormsCount(int allowedFormsCount) {
        this.allowedFormsCount = allowedFormsCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)

                .add("email", email)

                .add("name", name)
                .add("allowedFormsCount", allowedFormsCount)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PatientDto that = (PatientDto) o;

        return new EqualsBuilder()
                .append(allowedFormsCount, that.allowedFormsCount)
                .append(email, that.email)
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(email)
                .append(name)
                .append(allowedFormsCount)
                .toHashCode();
    }
}
