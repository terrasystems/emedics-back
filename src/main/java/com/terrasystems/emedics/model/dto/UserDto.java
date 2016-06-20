package com.terrasystems.emedics.model.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable {
    private static final long serialVersionUID = 1509556509931292216L;
    private String id;
    private String type;
    private String email;
    private String typeExp;
    private String firstName;
    private String lastName;
    private Date birth;
    private String phone;

    private String password;
    private String username;

    public UserDto() {}
    public UserDto(String email, String username) {
        //this.type = type;
        this.email = email;
        //this.password = password;
        this.username = username;
    }

    public String getTypeExp() {
        return typeExp;
    }

    public void setTypeExp(String typeExp) {
        this.typeExp = typeExp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("email", email)
                .add("password", password)
                .add("username", username)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return new EqualsBuilder()
                .append(id, userDto.id)
                .append(type, userDto.type)
                .append(email, userDto.email)
                .append(password, userDto.password)
                .append(username, userDto.username)
                .append(firstName, userDto.firstName)
                .append(lastName, userDto.lastName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(type)
                .append(email)
                .append(password)
                .append(username)
                .append(firstName)
                .append(lastName)
                .toHashCode();
    }
}
