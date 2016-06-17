package com.terrasystems.emedics.model.dto;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class ReferenceDto implements Serializable {
    private static final long serialVersionUID = 4609686150096381601L;

    private String id;
    private String name;
    private String type;
    private String phone;
    private String email;
    private boolean enabled;

    public ReferenceDto() {
    }

    public ReferenceDto(String id, String name, String type, String phone, String email) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("type", type)
                .add("phone", phone)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferenceDto)) return false;

        ReferenceDto referenceDto = (ReferenceDto) o;

        return  Objects.equal(id, referenceDto.id)&&
                Objects.equal(name, referenceDto.name) &&
                Objects.equal(type, referenceDto.type)&&
                Objects.equal(phone, referenceDto.phone);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, type, phone);
    }
}
