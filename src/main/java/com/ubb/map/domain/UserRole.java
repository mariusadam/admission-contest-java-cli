package com.ubb.map.domain;

import com.j256.ormlite.field.DatabaseField;

public class UserRole {
    public final static String USER_ID_FIELD_NAME = "user_id";
    public final static String ROLE_ID_FIELD_NAME = "role_id";

    @DatabaseField(foreign = true, columnName = USER_ID_FIELD_NAME)
    private User user;

    @DatabaseField(foreign = true, columnName = ROLE_ID_FIELD_NAME)
    private Role role;
}
