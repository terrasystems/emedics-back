package com.terrasystems.emedics.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Column(name = "readtype")
    private Boolean readtype;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    @Type(type = "text")
    private String text;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_form_id")
    private UserForm userForm;


    public Notification() {
    }

    public Notification(Date date, Boolean readtype, User fromUser, User toUser, String type, String title, String text) {
        this.date = date;
        this.readtype = readtype;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.type = type;
        this.title = title;
        this.text = text;
    }



    public UserForm getUserForm() {
        return userForm;
    }

    public void setUserForm(UserForm userForm) {
        this.userForm = userForm;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getReadtype() {
        return readtype;
    }

    public void setReadtype(Boolean readtype) {
        this.readtype = readtype;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
