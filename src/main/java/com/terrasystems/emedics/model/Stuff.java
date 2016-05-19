package com.terrasystems.emedics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stuff")
@DiscriminatorValue(value = "stuff")
public class Stuff extends User {


    @ManyToOne(fetch = FetchType.LAZY)
    private Organization organization;

    private boolean admin;

    public Stuff() {}
    public Stuff(String username, String password, String email) {
        super(username, password, email);
    }
    @JsonIgnore
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Stuff)) return false;

        Stuff stuff = (Stuff) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(admin, stuff.admin)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(admin)
                .toHashCode();
    }
}
