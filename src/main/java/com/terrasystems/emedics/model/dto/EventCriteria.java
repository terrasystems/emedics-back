package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class EventCriteria implements Serializable {
    private static final long serialVersionUID = -8221197681509633157L;
    private EventDto edit;
    private UserTemplateDto create;

    public EventCriteria() {}

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
