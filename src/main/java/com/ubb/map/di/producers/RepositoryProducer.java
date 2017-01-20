package com.ubb.map.di.producers;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.HasId;
import com.ubb.map.helper.config.Configuration;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.OrmRepository;
import com.ubb.map.repository.qualifiers.Connection;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.sql.SQLException;

@Singleton
public class RepositoryProducer {
    private final static String DEFAULT_CONFIG_PATH = "src/main/resources/config/config.yml";
    private Configuration config;

    public RepositoryProducer() {
        this(DEFAULT_CONFIG_PATH);
    }

    public RepositoryProducer(String configPath) {
        this.config = new Configuration(configPath);
    }

    @Produces
    @Connection
    public ConnectionSource getConnection() {
        try {
            return new JdbcConnectionSource(getDatabaseConfig("url"), getDatabaseConfig("user"), getDatabaseConfig("pass"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDatabaseConfig(String key) {
        return this.config.getDatabase().get(key);
    }

    private <Id, T extends HasId<Id>> RepositoryInterface<Id, T> getDbRepo(Class<Id> idClass, Class<T> entityClass) {
        return new OrmRepository<>(getConnection(), entityClass);
    }
}
