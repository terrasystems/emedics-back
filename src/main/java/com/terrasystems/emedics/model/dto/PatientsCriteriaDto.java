package com.terrasystems.emedics.model.dto;


import java.io.Serializable;
import java.util.List;

public class PatientsCriteriaDto implements Serializable {
    private static final long serialVersionUID = 683639151182509554L;

    private String search;
    private List<PatientDto> list;

    public PatientsCriteriaDto() {}

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<PatientDto> getList() {
        return list;
    }

    public void setList(List<PatientDto> list) {
        this.list = list;
    }
}
