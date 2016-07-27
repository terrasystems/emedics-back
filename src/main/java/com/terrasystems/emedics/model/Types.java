package com.terrasystems.emedics.model;


import com.terrasystems.emedics.enums.UserType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "types")
public class Types extends BaseEntity {

    private static final long serialVersionUID = -2537566422604593137L;

    @Column(name = "name")
    private String name;

    @Column(name = "userType")
    private UserType userType;

    public Types() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}
