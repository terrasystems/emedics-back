package com.terrasystems.emedics.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "forms")
public class Form {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "type")
    private String type;

    @Column(name = "category")
    private String category;

    @Column(name = "body")
    private String body;

    @Column(name = "descr")
    private String descr;

    @Column(name = "active")
    private boolean active = false;

    @ManyToMany(mappedBy = "forms")
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "blank_id")
    private Blank blank;

    public Form(){}
    public Form(String type, String category, String body, String descr) {
        this.type = type;
        this.category = category;
        this.body = body;
        this.descr = descr;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @JsonIgnore
    public Blank getBlank() {
        return blank;
    }

    public void setBlank(Blank blank) {
        this.blank = blank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
