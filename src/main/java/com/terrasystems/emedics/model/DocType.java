package com.terrasystems.emedics.model;


import com.terrasystems.emedics.enums.DocTypeEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doc_type")
public class DocType extends BaseEntity {

    private static final long serialVersionUID = 2755302903912676886L;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type", fetch = FetchType.EAGER, orphanRemoval = true)
    protected Set<Doctor> doctors;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private DocTypeEnum value;

    public DocType() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocTypeEnum getValue() {
        return value;
    }

    public void setValue(DocTypeEnum value) {
        this.value = value;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }
}
