package com.terrasystems.emedics.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "patients")
@DiscriminatorValue("patient")
public class Patient extends User {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;
*/
    public Patient() {}

    public Patient(String username, String email, String password){
        super(username, email, password);
    }

    /*public Patient(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }*/


}
