package com.terrasystems.emedics.model.dtoV2;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class ChangePasswordDto implements Serializable {

    private static final long serialVersionUID = 2031378736368729062L;
    private String oldPass;
    private String newPass;

    public ChangePasswordDto() {
    }

    public ChangePasswordDto(String oldPass, String newPass) {
        this.oldPass = oldPass;
        this.newPass = newPass;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("oldPass", oldPass)
                .append("newPass", newPass)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ChangePasswordDto that = (ChangePasswordDto) o;

        return new EqualsBuilder()
                .append(oldPass, that.oldPass)
                .append(newPass, that.newPass)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(oldPass)
                .append(newPass)
                .toHashCode();
    }
}
