package com.terrasystems.emedics.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


public class DashboardFormsRequest implements Serializable {
    private static final long serialVersionUID = 7948074866237898872L;

    private FormDashboardCriteriaDto criteria;

    private PageDto page;

    public DashboardFormsRequest(){}


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
