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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Doctor)) return false;

        Doctor doctor = (Doctor) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(type, doctor.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(type)
                .toHashCode();
    }
}
