package com.ubb.map.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by marius on 11.12.2016.
 */
@DatabaseTable(tableName = "users")
public class User extends Entity {
    @DatabaseField(columnName = "last_name")
    private String           lastName;
    @DatabaseField(columnName = "first_name")
    private String           firstName;
    @DatabaseField(columnName = "is_active")
    private Boolean          isActive;
    @DatabaseField(columnName = "logged_id")
    private Boolean          loggedIn;
    @DatabaseField(columnName = "last_login")
    private Date             lastLogin;

    @DatabaseField
    private String           email;
    @DatabaseField
    private String           password;

    public User() {

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
