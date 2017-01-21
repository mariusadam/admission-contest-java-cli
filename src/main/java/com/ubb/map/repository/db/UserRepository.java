package com.ubb.map.repository.db;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.User;
import com.ubb.map.di.qualifiers.Connection;
import com.ubb.map.exception.DuplicateEntryException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;

/**
 * Manager class for User entity
 */
@Singleton
public class UserRepository extends OrmRepository<Integer, User> {

    @Inject
    public UserRepository(@Connection ConnectionSource connection) {
        super(connection, User.class);
    }

    public User findByEmailAndPassword(String email, String password) throws SQLException {
        QueryBuilder<User, Integer> builder = this.dao.queryBuilder();
        builder.where().eq("email", email).and().eq("password", password).and().eq("is_active", 1);

        return this.dao.queryForFirst(builder.prepare());

    }

    public User findByEmail(String email) throws SQLException {
        return this.dao.queryForFirst(
                this
                        .dao
                        .queryBuilder()
                        .where()
                        .eq("email", email)
                        .and()
                        .eq("is_active", 1)
                        .prepare());
    }
}
