package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class EventCriteria implements Serializable {
    private static final long serialVersionUID = -8221197681509633157L;
    private EventDto edit;
    private UserTemplateDto create;
    private String toUser;
    private String event;

    public EventCriteria() {}

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public EventDto getEdit() {
        return edit;
    }

    public void setEdit(EventDto edit) {
        this.edit = edit;
    }

    public UserTemplateDto getCreate() {
        return create;
    }

    public void setCreate(UserTemplateDto create) {
        this.create = create;
    }
}
