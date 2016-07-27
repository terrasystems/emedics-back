package com.terrasystems.emedics.model;

import com.terrasystems.emedics.enums.TypeEnum;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "templates")
public class Template extends BaseEntity{

    private static final long serialVersionUID = -1077414207848205659L;

    public Template(String name, String category, String body, String descr, String number, boolean commercialEnum, TypeEnum typeEnum) {
        this.name = name;
        this.category = category;
        this.body = body;
        this.descr = descr;
        this.number = number;
        this.commercialEnum = commercialEnum;
        this.typeEnum = typeEnum;
    }

    public Template() {
        //bla
    }

    @Column(name = "name")
    private String name;


    @Column(name = "category")
    private String category;

    @Column(name = "body")
    @Type(type = "text")
    private String body;

    @Column(name = "descr")
    private String descr;

    @Column(name = "number")
    private String number;

    @Column(name = "commercial")
    private boolean commercialEnum;

    @Column(name = "med_type")
    private TypeEnum typeEnum;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isCommercialEnum() {
        return commercialEnum;
    }

    public void setCommercialEnum(boolean commercialEnum) {
        this.commercialEnum = commercialEnum;
    }

    public TypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }
}
