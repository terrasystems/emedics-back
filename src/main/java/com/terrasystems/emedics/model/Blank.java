package com.terrasystems.emedics.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "blanks")
public class Blank {

    public Blank(String type, String category, String body, String descr, String name, String number) {
        this.number = number;
        this.type = type;
        this.category = category;
        this.body = body;
        this.descr = descr;
        this.name = name;
    }

    public Blank() {

    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @JsonIgnore
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;

    @Column(name = "category")
    private String category;

    @Column(name = "body")
    @Type(type = "text")
    private String body;

    @Column(name = "descr")
    private String descr;

    @Column(name = "number")
    private String number;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blank")
    @Column(name = "userForms")
    private List<UserForm> userForms;


    @JsonIgnore
    public List<UserForm> getUserForms() {
        return userForms;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserForms(List<UserForm> userForms) {
        this.userForms = userForms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
