package com.terrasystems.emedics.model;




import javax.persistence.*;


@Entity
@Table(name = "patients")
@DiscriminatorValue("patient")
public class Patient extends User {

    @Column(name = "allowed_forms_count")
    private int allowedFormsCount;

    /*@ManyToMany(cascade = {CascadeType.MERGE} , fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_forms",
            joinColumns = @JoinColumn(name = "pat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "form_id", referencedColumnName = "id")
    )

    private List<UserForm> forms;

    public List<UserForm> getList() {
        return forms;
    }

    public void setUserForms(List<UserForm> forms) {
        this.forms = forms;
    }*/

    public Patient() {}

    public Patient(String username, String email, String password){
        super(username, email, password);
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

    /*public Patient(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }*/
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


}
