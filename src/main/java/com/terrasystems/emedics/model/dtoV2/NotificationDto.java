package com.terrasystems.emedics.model.dtoV2;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class NotificationDto implements Serializable {

    private static final long serialVersionUID = -7026766683780495132L;
    private String id;
    private String descr;
    private Date date;
    private String fromUserName;
    private String templateName;

    public NotificationDto() {
    }

    public NotificationDto(String id, String descr, Date date, String fromUserName, String templateName) {
        this.id = id;
        this.descr = descr;
        this.date = date;
        this.fromUserName = fromUserName;
        this.templateName = templateName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("descr", descr)
                .append("date", date)
                .append("fromUserName", fromUserName)
                .append("templateName", templateName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        NotificationDto that = (NotificationDto) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(descr, that.descr)
                .append(fromUserName, that.fromUserName)
                .append(templateName, that.templateName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(descr)
                .append(fromUserName)
                .append(templateName)
                .toHashCode();
    }
}
