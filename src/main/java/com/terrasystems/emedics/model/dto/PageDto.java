package com.terrasystems.emedics.model.dto;


import java.io.Serializable;

public class PageDto implements Serializable {
    private static final long serialVersionUID = 5357295997249009120L;
    private Long start;
    private Long count;
    private Long size;

    public PageDto(Long start, Long count, Long size) {
        this.start = start;
        this.count = count;
        this.size = size;
    }

    public PageDto() {
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
