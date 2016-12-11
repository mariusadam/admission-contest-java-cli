package com.ubb.map.domain;

import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by marius on 11.12.2016.
 */
public class User extends Entity {
    @ForeignCollectionField
    private Collection<Role> roles;
    private String           lastName;
    private String           firstName;
    private Boolean          isActive;
    private Boolean          loggedIn;
    private Date             lastLogin;
    private String           email;
    private String           password;

    public User() {
        this.roles = new ArrayList<>();
    }

    public User(Integer integer, Collection<Role> role, String firstName, String lastName) {
        super(integer);
        this.roles = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public User setRoles(Collection<Role> roles) {
        this.roles = roles;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Boolean isAllowed(Resource resource, Operation operation) {
        for (Role role : this.getRoles()) {
            if (role.getResource().equals(resource) && role.getAllowedOperations().contains(operation)) {
                return true;
            }
        }

        return false;
    }

    public Boolean isAllowed(Resource resource) {
        for (Operation operation : Operation.values()) {
            if (!this.isAllowed(resource, operation)) {
                return false;
            }
        }

        return true;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public User setIsActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public User setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public User setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toCsvFormat(String separator) {
        return null;
    }
}
