package com.terrasystems.emedics.model.dtoV2;


import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Types;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class ReferenceDto implements Serializable {

    private static final long serialVersionUID = 2125034027282121044L;
    private String id;
    private String name;
    private String firstName;
    private String lastName;
    private UserType userType;
    private Date dob;
    private Types type;
    private String email;
    private Boolean active;
    private String phone;

    public ReferenceDto() {
    }

    public ReferenceDto(String id, String name, String firstName, String lastName, UserType userType, Date dob, Types type, String email, Boolean active, String phone) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.dob = dob;
        this.type = type;
        this.email = email;
        this.active = active;
        this.phone = phone;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("userType", userType)
                .append("dob", dob)
                .append("type", type)
                .append("email", email)
                .append("active", active)
                .append("phone", phone)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ReferenceDto that = (ReferenceDto) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(email, that.email)
                .append(active, that.active)
                .append(phone, that.phone)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(firstName)
                .append(lastName)
                .append(email)
                .append(active)
                .append(phone)
                .toHashCode();
    }
}
