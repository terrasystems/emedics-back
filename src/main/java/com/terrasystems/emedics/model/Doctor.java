package com.terrasystems.emedics.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "doctors")
@DiscriminatorValue("doctor")
public class Doctor extends User {


    @ManyToOne(fetch = FetchType.LAZY)
    private DocType type;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "patients_doctors",
            joinColumns = @JoinColumn(name = "doc_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pat_id", referencedColumnName = "id")
    )
    private List<Patient> patients;


    public DocType getType() {
        return type;
    }

    public void setType(DocType type) {
        this.type = type;
    }

    @Column
    private Boolean admin;

    @Column(name = "org_name")
    private String orgName;
    @Column(name = "org_website")
    private String orgWebSite;
    @Column(name = "org_address")
    private String orgAddress;
    @Column(name = "org_description")
    private String orgDescription;
    @Column(name = "org_type")
    private String orgType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<Stuff> stuff;



    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }


    public Doctor(){
        super();
    }
    public Doctor(String username, String password, String email) {
        super(username, password, email);
    }

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgWebSite() {
        return orgWebSite;
    }

    public void setOrgWebSite(String orgWebSite) {
        this.orgWebSite = orgWebSite;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public List<Stuff> getStuff() {
        return stuff;
    }

    public void setStuff(List<Stuff> stuff) {
        this.stuff = stuff;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(admin, doctor.admin)
                .append(orgName, doctor.orgName)
                .append(orgWebSite, doctor.orgWebSite)
                .append(orgAddress, doctor.orgAddress)
                .append(orgDescription, doctor.orgDescription)
                .append(orgType, doctor.orgType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(admin)
                .append(orgName)
                .append(orgWebSite)
                .append(orgAddress)
                .append(orgDescription)
                .append(orgType)
                .toHashCode();
    }
}
