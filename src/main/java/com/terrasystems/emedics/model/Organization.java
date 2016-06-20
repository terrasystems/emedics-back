package com.terrasystems.emedics.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column
    private String name;
    @Column
    private String fullName;
    @Column
    private String website;
    @Column
    private String address;
    @Column
    private String descr;
    @Column
    String type;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Stuff> stuff;*/


    public Organization(String name, String fullName, String website, String address, String descr, String type) {
        this.name = name;
        this.fullName = fullName;
        this.website = website;
        this.address = address;
        this.descr = descr;
        this.type = type;

    }
    public Organization(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    /*public List<Stuff> getStuff() {
        return stuff;
    }

    public void setStuff(List<Stuff> stuff) {
        this.stuff = stuff;
    }*/
}
