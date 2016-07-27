package com.terrasystems.emedics.model.dtoV2;


import com.terrasystems.emedics.enums.UserType;

import java.io.Serializable;

public class CriteriaDto implements Serializable {

    private static final long serialVersionUID = -6811391987702531431L;
    private String search;
    private Integer count;
    private Integer start;
    private UserType type;

    public CriteriaDto() {
    }

    public CriteriaDto(String search, Integer count, Integer start, UserType type) {
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

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
