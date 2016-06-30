package com.terrasystems.emedics.model.dto;


import java.io.Serializable;
import java.util.Set;

public class ReferenceMobileCriteriaDto implements Serializable {
    private static final long serialVersionUID = -7429039318192090107L;

    private String search;
    private int start;
    private int count;
    private Set<String> list;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<String> getList() {
        return list;
    }

    public void setList(Set<String> list) {
        this.list = list;
    }
}
