package com.ubb.map.repository.db;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Role;
import com.ubb.map.repository.qualifiers.ConnectionSingleton;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RoleRepository extends OrmRepository<Integer, Role> {
    @Inject
    public RoleRepository(@ConnectionSingleton ConnectionSource connection) {
        super(connection, Role.class);
    }
}
