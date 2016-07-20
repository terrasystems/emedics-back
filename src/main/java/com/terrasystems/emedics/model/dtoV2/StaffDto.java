package com.terrasystems.emedics.model.dtoV2;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class StaffDto implements Serializable {

    private static final long serialVersionUID = 2859509076479988716L;
    private String id;
    private String name;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Date dob;
    private String address;

    public StaffDto() {
    }

    public StaffDto(String id, String name, String firstName, String lastName, String phone, String email, Date dob, String address) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
        this.address = address;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                .append("dob", dob)
                .append("address", address)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StaffDto staffDto = (StaffDto) o;

        return new EqualsBuilder()
                .append(id, staffDto.id)
                .append(name, staffDto.name)
                .append(firstName, staffDto.firstName)
                .append(lastName, staffDto.lastName)
                .append(phone, staffDto.phone)
                .append(email, staffDto.email)
                .append(address, staffDto.address)
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
                .append(address)
                .toHashCode();
    }
}
