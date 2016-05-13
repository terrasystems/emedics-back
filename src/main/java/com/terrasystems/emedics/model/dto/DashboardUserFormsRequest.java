package com.terrasystems.emedics.model.dto;

import java.io.Serializable;


public class DashboardUserFormsRequest implements Serializable {
    private static final long serialVersionUID = 7948074866237898872L;

    private FormDashboardCriteriaDto criteria;

    private PageDto page;

    public DashboardUserFormsRequest(){}


    public FormDashboardCriteriaDto getCriteria() {
        return criteria;
    }

    public void setCriteria(FormDashboardCriteriaDto criteria) {
        this.criteria = criteria;
    }

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }


}
