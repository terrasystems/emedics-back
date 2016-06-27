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


}
