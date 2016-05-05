package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class DashboardReferencesRequest implements Serializable{
    private static final long serialVersionUID = -8893658493797291657L;

    private ReferenceDashboardCriteriaDto criteria;
    private PageDto pageDto;

    public DashboardReferencesRequest() {
    }

    public DashboardReferencesRequest(ReferenceDashboardCriteriaDto criteria, PageDto pageDto) {
        this.criteria = criteria;
        this.pageDto = pageDto;
    }

    public ReferenceDashboardCriteriaDto getCriteria() {
        return criteria;
    }

    public void setCriteria(ReferenceDashboardCriteriaDto criteria) {
        this.criteria = criteria;
    }

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
