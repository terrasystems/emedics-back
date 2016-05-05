package com.terrasystems.emedics.model.dto;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class NotificationsDto implements Serializable{
    private static final long serialVersionUID = -920492853856119971L;
    private String id;
    private String timestamp;
    private String readtype;
    private String type;
    private String title;
    private String text;
    private UserDto userDto;

    public NotificationsDto() {
    }

    public NotificationsDto(String id, String timestamp, String readtype, String type, String title, String text) {
        this.id = id;
        this.timestamp = timestamp;
        this.readtype = readtype;
        this.type = type;
        this.title = title;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getReadtype() {
        return readtype;
    }

    public void setReadtype(String readtype) {
        this.readtype = readtype;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("timestamp", timestamp)
                .add("readtype", readtype)
                .add("type", type)
                .add("title", title)
                .add("text", text)
                .add("userDto", userDto)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationsDto)) return false;

        NotificationsDto notificationsDto = (NotificationsDto) o;
        return Objects.equal(id, notificationsDto.id)&&
                Objects.equal(timestamp, notificationsDto.timestamp)&&
                Objects.equal(readtype, notificationsDto.readtype)&&
                Objects.equal(type, notificationsDto.type)&&
                Objects.equal(title, notificationsDto.title)&&
                Objects.equal(userDto, notificationsDto.userDto)&&
                Objects.equal(text, notificationsDto.text);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, timestamp, readtype, type, title, text, userDto);
    }
}
