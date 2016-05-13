package com.terrasystems.emedics.model;



import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "user_forms")
public class UserForm /*extends BaseForm*/ {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "data")
    @Type(type = "text")
    private String data;



    @Column(name = "active")
    private boolean active = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    //TODO DTO
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "blank_id")
    private Blank blank;


    public UserForm() {}

    public UserForm(String data, boolean active, User user, Blank blank) {
        this.data = data;
        this.active = active;
        this.user = user;
        this.blank = blank;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getData() {
        return data;
    }


    public void setData(String data) {
        this.data = data;
    }
}