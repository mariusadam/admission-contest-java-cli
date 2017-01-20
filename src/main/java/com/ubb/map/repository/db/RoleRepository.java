package com.ubb.map.repository.db;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Role;
import com.ubb.map.di.qualifiers.Connection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RoleRepository extends OrmRepository<Integer, Role> {
    @Inject
    public RoleRepository(@Connection ConnectionSource connection) {
        super(connection, Role.class);
    }
}
