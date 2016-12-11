package com.ubb.map.helper;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.*;
import com.ubb.map.repository.db.*;

import java.sql.SQLException;

import com.ubb.map.helper.config.Configuration;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.qualifiers.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * @author Marius Adam
 */
public class ServiceContainer {
    private final static String DEFAULT_CONFIG_PATH = "src/main/resources/config/config.yml";
    private Configuration config;

    public ServiceContainer() {
        this(DEFAULT_CONFIG_PATH);
    }

    public ServiceContainer(String configPath) {
        this.config   = new Configuration(configPath);

        this.registerDefaultValues();
    }

    @Produces @ApplicationScoped @UserRoleRepo
    public UserRoleRepository getUserRoleRepo() {
        return new UserRoleRepository(getConnection());
    }

    @Produces @ApplicationScoped @OptionRepo
    public RepositoryInterface<Integer, Option> getOptionRepository() {
        return this.getDbRepo(Integer.class, Option.class);
    }

    @Produces @ApplicationScoped @CandidateRepo
    public RepositoryInterface<Integer, Candidate> getCandidateRepository() {
        return this.getDbRepo(Integer.class, Candidate.class);
    }

    @Produces @ApplicationScoped @DepartmentRepo
    public RepositoryInterface<Integer, Department> getDepartmentRepository() {
        return this.getDbRepo(Integer.class, Department.class);
    }

    private String getDatabaseConfig(String key) {
        return this.config.getDatabase().get(key);
    }

    private ConnectionSource getConnection() {
        try {
            return new JdbcConnectionSource(getDatabaseConfig("url"), getDatabaseConfig("user"), getDatabaseConfig("pass"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <Id, T extends HasId<Id>> RepositoryInterface<Id, T> getDbRepo(Class<Id> idClass, Class<T> entityClass) {
        return new OrmRepository<>(getConnection(), entityClass);
    }

    private void registerDefaultValues() {

    }
}
