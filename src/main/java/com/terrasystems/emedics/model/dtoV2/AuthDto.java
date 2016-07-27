package com.terrasystems.emedics.model.dtoV2;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class AuthDto implements Serializable {

    private UserDto userDto;
    private String token;

    public AuthDto(UserDto userDto, String token) {
        this.userDto = userDto;
        this.token = token;
    }

    public AuthDto() {
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userDto", userDto)
                .append("token", token)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AuthDto)) return false;

        AuthDto authDto = (AuthDto) o;

        return new EqualsBuilder()
                .append(getToken(), authDto.getToken())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getToken())
                .toHashCode();
    }
}
