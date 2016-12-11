package com.ubb.map.di.producers;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.*;
import com.ubb.map.repository.db.*;

import java.sql.SQLException;

import com.ubb.map.helper.config.Configuration;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.qualifiers.*;
import com.ubb.map.services.CandidateCrudService;
import com.ubb.map.services.DepartmentCrudService;
import com.ubb.map.services.OptionCrudService;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class RepositoryProducer {
    private final static String DEFAULT_CONFIG_PATH = "src/main/resources/config/config.yml";
    private Configuration config;

    public RepositoryProducer() {
        this(DEFAULT_CONFIG_PATH);
    }

    public RepositoryProducer(String configPath) {
        this.config   = new Configuration(configPath);

        this.registerDefaultValues();
    }

    @Produces @Singleton
    public UserRoleRepository getUserRoleRepo() {
        return new UserRoleRepository(getConnection());
    }

    @Produces @OptionRepo
    public RepositoryInterface<Integer, Option> getOptionRepository() {
        return this.getDbRepo(Integer.class, Option.class);
    }

    @Produces @CandidateRepo
    public RepositoryInterface<Integer, Candidate> getCandidateRepository() {
        return this.getDbRepo(Integer.class, Candidate.class);
    }

    @Produces @DepartmentRepo
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

    public CandidateCrudService getCandidateConttroller() {
        return null;
    }

    public DepartmentCrudService getDepartmentController() {
        return null;
    }

    public OptionCrudService getOptionController() {
        return null;
    }
}
