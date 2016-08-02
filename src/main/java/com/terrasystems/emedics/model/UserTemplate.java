package com.terrasystems.emedics.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.terrasystems.emedics.enums.FormEnum;
import com.terrasystems.emedics.enums.TypeEnum;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_templates")
public class UserTemplate extends BaseEntity{

    private static final long serialVersionUID = -4933760370608608485L;
    @Column(name = "type")
    private TypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "description")
    private String description;

    public UserTemplate() {}

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserTemplate that = (UserTemplate) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(description, that.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(description)
                .toHashCode();
    }
}