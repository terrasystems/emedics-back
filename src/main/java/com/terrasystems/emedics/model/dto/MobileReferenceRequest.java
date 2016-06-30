package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class MobileReferenceRequest implements Serializable {
    private static final long serialVersionUID = 2702940979200592657L;

    private ReferenceMobileCriteriaDto criteriaDto;
    private PageDto pageDto;

    public ReferenceMobileCriteriaDto getCriteriaDto() {
        return criteriaDto;
    }

    public void setCriteriaDto(ReferenceMobileCriteriaDto criteriaDto) {
        this.criteriaDto = criteriaDto;
    }

    public PageDto getPageDto() {
        return pageDto;
    }

    public void setPageDto(PageDto pageDto) {
        this.pageDto = pageDto;
    }
}
