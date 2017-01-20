package com.ubb.map.repository.db;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Role;
import com.ubb.map.domain.User;
import com.ubb.map.domain.UserRole;
import com.ubb.map.repository.qualifiers.Connection;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Manager for UserRole entity
 */
@Singleton
public class UserRoleRepository extends OrmRepository<Integer, UserRole> {

    @Inject
    private RoleRepository roleRepository;

    @Inject
    public UserRoleRepository(@Connection ConnectionSource connection) {
        super(connection, UserRole.class);
    }

    public Collection<Role> getRoles(User user) {
        try {
            //create the inner query
            QueryBuilder<UserRole, Integer> userRoleQb = dao.queryBuilder();
            // this time selecting for the user-id field
            userRoleQb.selectColumns(UserRole.ROLE_ID_FIELD_NAME);
            userRoleQb.where().eq(UserRole.USER_ID_FIELD_NAME, user);

            // build our outer query
            QueryBuilder<Role, Integer> roleQb = roleRepository.dao.queryBuilder();
            // where the user-id matches the inner query's user-id field
            roleQb.where().in("id", userRoleQb);

            return roleRepository.dao.query(roleQb.prepare());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
