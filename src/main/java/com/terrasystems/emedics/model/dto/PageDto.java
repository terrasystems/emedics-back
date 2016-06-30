package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class PageDto implements Serializable {
    private static final long serialVersionUID = 5357295997249009120L;
    private Integer start;
    private Integer count;
    private Integer size;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
