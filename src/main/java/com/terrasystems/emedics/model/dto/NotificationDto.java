package com.terrasystems.emedics.model.dto;


import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;

public class NotificationDto implements Serializable {
    private static final long serialVersionUID = -920492853856119971L;
    private String id;
    private Date date;
    private Boolean readtype;
    private String type;
    private String title;
    private String text;
    private UserDto fromUser;
    private UserDto toUser;
    private String userForm;

    public NotificationDto() {
    }

    public NotificationDto(Date date, Boolean readtype, String type, String title, String text, UserDto fromUser, UserDto toUser, String userForm) {
        this.date = date;
        this.readtype = readtype;
        this.type = type;
        this.title = title;
        this.text = text;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.userForm = userForm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserForm() {
        return userForm;
    }

    public void setUserForm(String userForm) {
        this.userForm = userForm;
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

    public UserDto getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDto fromUser) {
        this.fromUser = fromUser;
    }

    public UserDto getToUser() {
        return toUser;
    }

    public void setToUser(UserDto toUser) {
        this.toUser = toUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getReadtype() {
        return readtype;
    }

    public void setReadtype(Boolean readtype) {
        this.readtype = readtype;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("date", date)
                .add("readtype", readtype)
                .add("type", type)
                .add("title", title)
                .add("text", text)
                .add("fromUser", fromUser)
                .add("toUser", toUser)
                .add("userForm", userForm)
                .toString();
    }
}


