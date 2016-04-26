package com.terrasystems.emedics.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


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
    private String clinic;

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


}
