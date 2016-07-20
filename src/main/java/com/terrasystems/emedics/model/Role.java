package com.terrasystems.emedics.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority{
    private static final long serialVersionUID = -2271830859183553214L;

    public Role() {}

    public Role(String name){
        this.name = name;
    }

    @Column(name = "role_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public String getAuthority() {
        return this.name;
    }
}
