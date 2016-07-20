package com.terrasystems.emedics.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.terrasystems.emedics.enums.UserType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISC", discriminatorType = DiscriminatorType.STRING, length = 15)
public class User extends BaseEntity implements UserDetails{

    private static final long serialVersionUID = 560754003970679875L;

    public User() {
        //bla
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }



    @Column(name = "is_org")
    protected Boolean org = false;

    @Column(name = "name")
    protected String name;

    @Column
    protected String firstName;

    @Column
    protected String lastName;

    @Column
    protected String phone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birth_date")
    protected Date birth;

    @Column
    protected String activationToken;

    @Column
    protected String valueKey;

    @Column
    protected Date registrationDate;

    @Column(name = "user_type")
    protected UserType userType;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false)
    protected String email;

    @Column
    protected String typeExp;

    @Column
    protected Long expires;

    @Column
    protected boolean enabled = false;

    @Column(name = "allowed_forms_count")
    protected int allowedFormsCount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Role> roles;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserTemplate> userTemplates;

    @ManyToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            mappedBy = "userRef"

    )
    private Set<User> users;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_users",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_ref_id", referencedColumnName = "id")
    )
    private Set<User> userRef;

    public List<UserTemplate> getUserTemplates() {
        return userTemplates;
    }

    public void setUserTemplates(List<UserTemplate> userTemplates) {
        this.userTemplates = userTemplates;
    }

    public String getTypeExp() {
        return typeExp;
    }

    public void setTypeExp(String typeExp) {
        this.typeExp = typeExp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUserRef() {
        return userRef;
    }

    public void setUserRef(Set<User> userRef) {
        this.userRef = userRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public int getAllowedFormsCount() {
        return allowedFormsCount;
    }

    public void setAllowedFormsCount(int allowedFormsCount) {
        this.allowedFormsCount = allowedFormsCount;
    }

    public Boolean getOrg() {
        return org;
    }

    public void setOrg(Boolean org) {
        this.org = org;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    //TODO add fields for accaunt disabelin(Expirision, Locked etc)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Transient
    public String getDiscriminatorValue() {
        DiscriminatorValue val = this.getClass().getAnnotation(DiscriminatorValue.class);

        return val == null ? null : val.value();
    }


    public String resetPassword(String newPass) {
        setPassword(newPass);
        return newPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User)) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(enabled, user.enabled)
                .append(id, user.id)
                .append(name, user.name)
                .append(phone, user.phone)
                .append(valueKey, user.valueKey)
                .append(registrationDate, user.registrationDate)
                .append(password, user.password)
                .append(email, user.email)
                .append(expires, user.expires)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(phone)
                .append(registrationDate)
                .append(password)
                .append(email)
                .append(expires)
                .append(enabled)
                .append(valueKey)
                .toHashCode();
    }

    @PrePersist
    public void preInsert() {
        allowedFormsCount = 5;
        if ((firstName == null) && (lastName == null)) {
            name = email;
        } else if (firstName == null) {
            name = lastName;
        } else if (lastName == null) {
            name = firstName;
        } else name = firstName + " " + lastName;
        if (org == null) org = false;
    }
}
