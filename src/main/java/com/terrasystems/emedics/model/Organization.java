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
    private String link;
    @Column
    private String address;
    @Column
    private String descr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organization", fetch = FetchType.LAZY)
    private List<Stuff> stuff;


    public Organization(String name, String fullName, String link, String address, String descr) {
        this.name = name;
        this.fullName = fullName;
        this.link = link;
        this.address = address;
        this.descr = descr;

    }
    public Organization(){}

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public List<Stuff> getStuff() {
        return stuff;
    }

    public void setStuff(List<Stuff> stuff) {
        this.stuff = stuff;
    }
}
