package com.terrasystems.emedics.model.dtoV2;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class PatientDto implements Serializable {

    private static final long serialVersionUID = -8637790987855938086L;
    private String id;
    private String name;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    public PatientDto() {
    }

    public PatientDto(String id, String name, String firstName, String lastName, String phone, String email) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("phone", phone)
                .append("email", email)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PatientDto that = (PatientDto) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(phone, that.phone)
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(firstName)
                .append(lastName)
                .append(phone)
                .append(email)
                .toHashCode();
    }
}
