package com.terrasystems.emedics.model;




import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "patients")
@DiscriminatorValue("patient")
public class Patient extends User {

    @Column(name = "allowed_forms_count")
    private int allowedFormsCount;

    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors;


    public Patient() {}

    public Patient(String username, String email, String password){
        super(username, email, password);
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public int getAllowedFormsCount() {
        return allowedFormsCount;
    }

    public void setAllowedFormsCount(int allowedFormsCount) {
        this.allowedFormsCount = allowedFormsCount;
    }

    @PrePersist
    public void preInsert() {
        allowedFormsCount = 5;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("allowedFormsCount", allowedFormsCount)
                .add("doctors", doctors)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(allowedFormsCount, patient.allowedFormsCount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(allowedFormsCount)
                .toHashCode();
    }
}
