package com.terrasystems.emedics.model.dtoV2;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class LoginDto implements Serializable {

    private static final long serialVersionUID = 2987635504584330619L;
    private String email;
    private String pass;

    public LoginDto() {
    }

    public LoginDto(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("pass", pass)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LoginDto loginDto = (LoginDto) o;

        return new EqualsBuilder()
                .append(email, loginDto.email)
                .append(pass, loginDto.pass)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(email)
                .append(pass)
                .toHashCode();
    }
}
