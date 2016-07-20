package com.terrasystems.emedics.model.dtoV2;


import java.io.Serializable;

public class CriteriaDto implements Serializable {

    private static final long serialVersionUID = -6811391987702533531L;
    private String search;
    private Integer count;
    private Integer start;
    private String type;

    public CriteriaDto() {
    }

    public CriteriaDto(String search, Integer count, Integer start, String type) {
        this.search = search;
        this.count = count;
        this.start = start;
        this.type = type;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
