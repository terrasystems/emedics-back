package com.terrasystems.emedics.model.dto;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;


public class ResetPasswordDto implements Serializable{
    private static final long serialVersionUID = 3127332645811930970L;
    String validKey;
    String newPassword;

    public ResetPasswordDto() {
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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("validKey", validKey)
                .add("newPassword", newPassword)
                .toString();
    }
}
