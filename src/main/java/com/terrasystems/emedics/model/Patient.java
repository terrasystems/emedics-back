package com.terrasystems.emedics.model;




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

    /*@ManyToMany(cascade = {CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_forms",
            joinColumns = @JoinColumn(name = "pat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "form_id", referencedColumnName = "id")
    )

    private List<Form> forms;

    public List<Form> getList() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }*/

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



}
