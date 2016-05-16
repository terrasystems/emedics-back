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
    private String fromId;
    private String toId;

    public NotificationsDto() {
    }

    public NotificationsDto(String timestamp, String readtype, String type, String title, String text, String fromId, String toId) {
        this.timestamp = timestamp;
        this.readtype = readtype;
        this.type = type;
        this.title = title;
        this.text = text;
        this.fromId = fromId;
        this.toId = toId;
    }

    public String getId() {
        return id;
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

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
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
                .add("toId", toId)
                .add("fromId", fromId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationsDto)) return false;
        NotificationsDto that = (NotificationsDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(timestamp, that.timestamp) &&
                Objects.equal(readtype, that.readtype) &&
                Objects.equal(type, that.type) &&
                Objects.equal(title, that.title) &&
                Objects.equal(text, that.text) &&
                Objects.equal(fromId, that.fromId) &&
                Objects.equal(toId, that.toId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, timestamp, readtype, type, title, text, fromId, toId);
    }
}
