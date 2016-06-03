package com.terrasystems.emedics.model.dto;


import com.google.common.base.Objects;

import java.io.Serializable;

public class UserTemplateDto implements Serializable{
    private static final long serialVersionUID = 6270274520343122805L;
    private String id;
    private String type;
    private String description;
    private TemplateDto templateDto;

    public UserTemplateDto() {
    }

    public UserTemplateDto(String id, String type, String description, TemplateDto templateDto) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.templateDto = templateDto;
    }

    public TemplateDto getTemplateDto() {
        return templateDto;
    }

    public void setTemplateDto(TemplateDto templateDto) {
        this.templateDto = templateDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("description", description)
                .add("templateDto", templateDto)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTemplateDto)) return false;
        UserTemplateDto that = (UserTemplateDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(type, that.type) &&
                Objects.equal(description, that.description) &&
                Objects.equal(templateDto, that.templateDto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, type, description, templateDto);
    }
}
