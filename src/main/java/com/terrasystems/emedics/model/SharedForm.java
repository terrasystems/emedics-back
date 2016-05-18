package com.terrasystems.emedics.model;

import javax.persistence.*;

@Entity
//@Table(name = "shared_forms")
public class SharedForm extends BaseForm {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "blank_id")
    private Blank blank;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public SharedForm() {super();}

    public SharedForm(String data, User user, Blank blank, Patient patient) {
        super(data);
        this.user = user;
        this.blank = blank;
        this.patient = patient;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blank getBlank() {
        return blank;
    }

    public void setBlank(Blank blank) {
        this.blank = blank;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
