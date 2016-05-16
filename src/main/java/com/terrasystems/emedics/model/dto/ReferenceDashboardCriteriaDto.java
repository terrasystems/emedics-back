package com.terrasystems.emedics.model.dto;


import java.io.Serializable;
import java.util.Set;

public class ReferenceDashboardCriteriaDto implements Serializable {
    private static final long serialVersionUID = 1695478627620444793L;

    private String search;
    private Set<String> list;

    public ReferenceDashboardCriteriaDto() {
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Set<String> getList() {
        return list;
    }

    public void setList(Set<String> list) {
        this.list = list;
    }

}
