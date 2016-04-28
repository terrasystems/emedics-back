package com.terrasystems.emedics.model.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class UserDto implements Serializable {
    private static final long serialVersionUID = 1509556509931292216L;
    private String type;
    private String email;

    private String password;
    private String username;

    public UserDto() {}
    public UserDto(String email, String username) {
        //this.type = type;
        this.email = email;
        //this.password = password;
        this.username = username;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .add("password", password)
                .add("username", username)
                .add("type", type)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equal(email, userDto.email) &&
                Objects.equal(password, userDto.password) &&
                Objects.equal(username, userDto.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email, password, username);
    }
}