package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class DashboardEventRequest implements Serializable {
    private static final long serialVersionUID = 6538492383617120033L;
    private PageDto page;
    private EventCriteria criteria;
    private Object model;

    DashboardEventRequest() {}

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    public EventCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(EventCriteria criteria) {
        this.criteria = criteria;
    }
}
