package com.terrasystems.emedics.model.dto;


import java.io.Serializable;
import java.util.Date;

public class ReferenceCreateRequest implements Serializable{
    private static final long serialVersionUID = 4925787442039444625L;
    private String email;
    private String type;
    private String firstName;
    private String lastName;
    private Date birth;
    private String docType;

    public ReferenceCreateRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}
