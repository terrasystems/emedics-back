package com.terrasystems.emedics.model;


import com.terrasystems.emedics.enums.DocTypeEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "types")
public class Types extends BaseEntity {

    private static final long serialVersionUID = -2537566422604593137L;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type", fetch = FetchType.EAGER, orphanRemoval = true)
    protected Set<User> users;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private DocTypeEnum value;

    public Types() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocTypeEnum getValue() {
        return value;
    }

    public void setValue(DocTypeEnum value) {
        this.value = value;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
