package com.terrasystems.emedics.model;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "doctors")
@DiscriminatorValue("doctor")
public class Doctor extends User {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;*/
    private String type;
    private String clinic;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "patients_doctors",
            joinColumns = @JoinColumn(name = "pat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "doc_id", referencedColumnName = "id")
    )
    private List<Patient> patients;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
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
                .append(clinic, doctor.clinic)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(type)
                .append(clinic)
                .toHashCode();
    }
}
