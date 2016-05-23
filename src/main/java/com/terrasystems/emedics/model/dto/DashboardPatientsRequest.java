package com.terrasystems.emedics.model.dto;

import java.io.Serializable;

public class DashboardPatientsRequest implements Serializable {
    private static final long serialVersionUID = 5051265722465477948L;

    private PageDto page;
    private PatientsCriteriaDto criteria;

    public DashboardPatientsRequest() {}

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    public PatientsCriteriaDto getCriteria() {
        return criteria;
    }

    public void setCriteria(PatientsCriteriaDto criteria) {
        this.criteria = criteria;
    }
}
