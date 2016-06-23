package com.terrasystems.emedics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stuff")
@DiscriminatorValue(value = "stuff")
public class Stuff extends User {


    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;

    public Stuff() {super();}
    public Stuff(String username, String password, String email) {
        super(username, password, email);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Stuff stuff = (Stuff) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(doctor, stuff.doctor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(doctor)
                .toHashCode();
    }
}
