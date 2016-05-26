package com.terrasystems.emedics.model.dto;


import com.google.common.base.Objects;

import java.io.Serializable;

public class ChangePasswordDto implements Serializable{
    private static final long serialVersionUID = -9020027251484165347L;
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
        return Objects.toStringHelper(this)
                .add("oldPass", oldPass)
                .add("newPass", newPass)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangePasswordDto)) return false;
        ChangePasswordDto that = (ChangePasswordDto) o;
        return Objects.equal(oldPass, that.oldPass) &&
                Objects.equal(newPass, that.newPass);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(oldPass, newPass);
    }
}
