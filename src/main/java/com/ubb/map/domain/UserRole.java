package com.ubb.map.domain;

import com.j256.ormlite.field.DatabaseField;

public class UserRole extends Entity{
    public final static String USER_ID_FIELD_NAME = "user_id";
    public final static String ROLE_ID_FIELD_NAME = "role_id";

    @DatabaseField(foreign = true, columnName = USER_ID_FIELD_NAME)
    private User user;

    @DatabaseField(foreign = true, columnName = ROLE_ID_FIELD_NAME)
    private Role role;

    public UserRole() {
    }

    public User getUser() {
        return user;
    }

    public UserRole setUser(User user) {
        this.user = user;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public UserRole setRole(Role role) {
        this.role = role;
        return this;
    }
}
