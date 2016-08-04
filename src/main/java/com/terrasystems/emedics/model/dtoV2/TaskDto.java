package com.terrasystems.emedics.model.dtoV2;


import com.fasterxml.jackson.databind.JsonNode;
import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class TaskDto implements Serializable {

    private static final long serialVersionUID = 3691342243947887480L;
    private String id;
    private UserDto fromUser;
    private UserDto toUser;
    private String descr;
    private TemplateDto templateDto;
    private UserDto patient;
    private Date date;
    private StatusEnum status;
    private JsonNode model;
    private String _id;
    private String _rev;
    private String _type;

    public TaskDto() {
    }

    public TaskDto(String id) {
        this.id = id;
    }

    public TaskDto(String id, UserDto fromUser, UserDto toUser, String descr, TemplateDto templateDto, UserDto patient, Date date, StatusEnum status, JsonNode model, String _id, String _rev, String _type) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.descr = descr;
        this.templateDto = templateDto;
        this.patient = patient;
        this.date = date;
        this.status = status;
        this.model = model;
        this._id = _id;
        this._rev = _rev;
        this._type = _type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public TemplateDto getTemplateDto() {
        return templateDto;
    }

    public void setTemplateDto(TemplateDto templateDto) {
        this.templateDto = templateDto;
    }

    public UserDto getPatient() {
        return patient;
    }

    public void setPatient(UserDto patient) {
        this.patient = patient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public JsonNode getModel() {
        return model;
    }

    public void setModel(JsonNode model) {
        this.model = model;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("fromUser", fromUser)
                .append("toUser", toUser)
                .append("descr", descr)
                .append("templateDto", templateDto)
                .append("patient", patient)
                .append("date", date)
                .append("status", status)
                .append("model", model)
                .append("_id", _id)
                .append("_rev", _rev)
                .append("_type", _type)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TaskDto taskDto = (TaskDto) o;

        return new EqualsBuilder()
                .append(id, taskDto.id)
                .append(descr, taskDto.descr)
                .append(_id, taskDto._id)
                .append(_rev, taskDto._rev)
                .append(_type, taskDto._type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(descr)
                .append(_id)
                .append(_rev)
                .append(_type)
                .toHashCode();
    }
}
