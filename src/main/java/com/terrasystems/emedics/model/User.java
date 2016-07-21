package com.terrasystems.emedics.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.terrasystems.emedics.enums.UserType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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


    private static final long serialVersionUID = -3737168202495827435L;

    public User() {
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }



    @Column(name = "is_org", nullable = false)
    protected boolean isAdmin = false;

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
    protected boolean enabled = false;

    //TODO
    @Column(name = "allowed_forms_count", nullable = false)
    protected int allowedFormsCount = 5;

    @ManyToOne(fetch = FetchType.LAZY)
    private Types type;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User userOrg;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Role> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "org_id")
    private Organization organization;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<UserTemplate> userTemplates;

    @ManyToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            mappedBy = "references"

    )
    private Set<User> users;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_references",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_ref_id", referencedColumnName = "id")
    )
    private Set<User> references;



    @Transient
    public String getDiscriminatorValue() {
        DiscriminatorValue val = this.getClass().getAnnotation(DiscriminatorValue.class);

        return val == null ? null : val.value();
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeExp() {
        return typeExp;
    }

    public void setTypeExp(String typeExp) {
        this.typeExp = typeExp;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getAllowedFormsCount() {
        return allowedFormsCount;
    }

    public void setAllowedFormsCount(int allowedFormsCount) {
        this.allowedFormsCount = allowedFormsCount;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public User getUserOrg() {
        return userOrg;
    }

    public void setUserOrg(User userOrg) {
        this.userOrg = userOrg;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<UserTemplate> getUserTemplates() {
        return userTemplates;
    }

    public void setUserTemplates(List<UserTemplate> userTemplates) {
        this.userTemplates = userTemplates;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getReferences() {
        return references;
    }

    public void setReferences(Set<User> references) {
        this.references = references;
    }

    public String resetPassword(String newPass) {
        setPassword(newPass);
        return newPass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("isAdmin", isAdmin)
                .append("name", name)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("phone", phone)
                .append("birth", birth)
                .append("activationToken", activationToken)
                .append("valueKey", valueKey)
                .append("registrationDate", registrationDate)
                .append("userType", userType)
                .append("password", password)
                .append("email", email)
                .append("typeExp", typeExp)
                .append("enabled", enabled)
                .append("allowedFormsCount", allowedFormsCount)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User)) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(isAdmin(), user.isAdmin())
                .append(isEnabled(), user.isEnabled())
                .append(getAllowedFormsCount(), user.getAllowedFormsCount())
                .append(getName(), user.getName())
                .append(getFirstName(), user.getFirstName())
                .append(getLastName(), user.getLastName())
                .append(getPhone(), user.getPhone())
                .append(getBirth(), user.getBirth())
                .append(getActivationToken(), user.getActivationToken())
                .append(getValueKey(), user.getValueKey())
                .append(getRegistrationDate(), user.getRegistrationDate())
                .append(getPassword(), user.getPassword())
                .append(getEmail(), user.getEmail())
                .append(getTypeExp(), user.getTypeExp())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(isAdmin())
                .append(getName())
                .append(getFirstName())
                .append(getLastName())
                .append(getPhone())
                .append(getBirth())
                .append(getActivationToken())
                .append(getValueKey())
                .append(getRegistrationDate())
                .append(getPassword())
                .append(getEmail())
                .append(getTypeExp())
                .append(isEnabled())
                .append(getAllowedFormsCount())
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
    }
}
