package com.terrasystems.emedics.model.dto;

import java.io.Serializable;

public class EventEditRequest implements Serializable{
    private static final long serialVersionUID = 196854318154942037L;
    private EventDto event;

    public EventEditRequest() {}



    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }
}
