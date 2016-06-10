package com.terrasystems.emedics.model.dto;


import java.io.Serializable;
import java.util.List;

public class TemplateEventDto implements Serializable{
    private static final long serialVersionUID = 5796527708108677990L;

    private String id;
    private String name;
    private List<EventDto> events;

    public TemplateEventDto () {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventDto> events) {
        this.events = events;
    }
}
