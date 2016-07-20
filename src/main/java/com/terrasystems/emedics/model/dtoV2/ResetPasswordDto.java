package com.terrasystems.emedics.model.dtoV2;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ResetPasswordDto implements Serializable {

    private static final long serialVersionUID = -7957232064007240306L;
    private String validKey;
    private String newPassword;

    public ResetPasswordDto() {
    }

    public ResetPasswordDto(String validKey, String newPassword) {
        this.validKey = validKey;
        this.newPassword = newPassword;
    }

    public String getValidKey() {
        return validKey;
    }

    public void setValidKey(String validKey) {
        this.validKey = validKey;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("validKey", validKey)
                .append("newPassword", newPassword)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ResetPasswordDto that = (ResetPasswordDto) o;

        return new EqualsBuilder()
                .append(validKey, that.validKey)
                .append(newPassword, that.newPassword)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(validKey)
                .append(newPassword)
                .toHashCode();
    }
}
