package com.terrasystems.emedics.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.terrasystems.emedics.enums.FormEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_templates")
public class UserTemplate {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @JsonIgnore
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "active")
    private boolean active = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "commercial")
    private FormEnum commercial;

    public UserTemplate() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public FormEnum getCommercial() {
        return commercial;
    }

    public void setCommercial(FormEnum commercial) {
        this.commercial = commercial;
    }
}