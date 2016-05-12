package com.terrasystems.emedics.model;

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
}
