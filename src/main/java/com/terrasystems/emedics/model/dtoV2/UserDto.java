package com.terrasystems.emedics.model.dtoV2;


import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Types;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable {

    private static final long serialVersionUID = -4420973826814482388L;
    private String id;
    private UserType userType;
    private Types type;
    private String name;
    private String firstName;
    private String lastName ;
    private String pass;
    private Date dob;
    private String email;
    private String phone;
    private String address;
    private String orgName;
    private String website;
    private Boolean isAdmin;
    private String descr;

    public UserDto() {
    }

    public UserDto(String id) {
        this.id = id;
    }

    public UserDto(String id, UserType userType, Types type, String name, String firstName, String lastName, String pass, Date dob, String email, String phone, String address, String orgName, String website, Boolean isAdmin,String descr) {
        this.id = id;
        this.userType = userType;
        this.type = type;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pass = pass;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.orgName = orgName;
        this.website = website;
        this.isAdmin = isAdmin;
        this.descr = descr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userType", userType)
                .append("type", type)
                .append("name", name)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("pass", pass)
                .append("dob", dob)
                .append("email", email)
                .append("phone", phone)
                .append("address", address)
                .append("orgName", orgName)
                .append("website", website)
                .append("isAdmin", isAdmin)
                .append("descr", descr)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return new EqualsBuilder()
                .append(id, userDto.id)
                .append(name, userDto.name)
                .append(firstName, userDto.firstName)
                .append(lastName, userDto.lastName)
                .append(pass, userDto.pass)
                .append(email, userDto.email)
                .append(phone, userDto.phone)
                .append(address, userDto.address)
                .append(orgName, userDto.orgName)
                .append(website, userDto.website)
                .append(isAdmin, userDto.isAdmin)
                .append(descr, userDto.descr)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(firstName)
                .append(lastName)
                .append(pass)
                .append(email)
                .append(phone)
                .append(address)
                .append(orgName)
                .append(website)
                .append(isAdmin)
                .append(descr)
                .toHashCode();
    }
}
