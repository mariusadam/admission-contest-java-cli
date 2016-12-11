package com.ubb.map.helper;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.controller.CandidateController;
import com.ubb.map.controller.DepartmentController;
import com.ubb.map.controller.OptionController;
import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.HasId;
import com.ubb.map.domain.Option;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ubb.map.helper.config.Configuration;
import com.ubb.map.helper.loader.file.FileLoaderInterface;
import com.ubb.map.helper.mapper.CandidateMapper;
import com.ubb.map.helper.mapper.DepartmentMapper;
import com.ubb.map.helper.mapper.MapperInterface;
import com.ubb.map.helper.mapper.OptionMapper;
import com.ubb.map.helper.saver.FileSaverInterface;
import com.ubb.map.repository.Repository;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.DbRepository;
import com.ubb.map.repository.db.OrmRepository;
import com.ubb.map.repository.decorator.FileLoadingRepository;
import com.ubb.map.repository.decorator.FileSavingRepository;
import com.ubb.map.validator.CandidateValidator;
import com.ubb.map.validator.DepartmentValidator;
import com.ubb.map.validator.OptionValidator;
import com.ubb.map.validator.ValidatorInterface;

/**
 * @author Marius Adam
 */
public class ServiceContainer {
    private Configuration config;
    private Map<String, Object>  services;

    public ServiceContainer(String configPath) {

        this.config   = new Configuration(configPath);
        this.services = new HashMap<>();

        this.registerDefaultValues();
    }

    public RepositoryInterface<Integer, Option> getOptionRepository() {
        try {
            return this.getDbRepo(Integer.class, Option.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RepositoryInterface<Integer, Candidate> getCandidateRepository() {
        try {
            return this.getDbRepo(Integer.class, Candidate.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RepositoryInterface<Integer, Department> getDepartmentRepository() {
        try {
            return this.getDbRepo(Integer.class, Department.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> ValidatorInterface<T> getValidator(Class<T> tClass) {
        if (this.services.containsKey(tClass.getName() + ".com.ubb.map.validator")){
            //noinspection unchecked
            return (ValidatorInterface<T>) this.services.get(tClass.getName() + ".com.ubb.map.validator");
        }

        throw new RuntimeException("Could not find a com.ubb.map.validator for entity " + tClass.getName());
    }

    public CandidateController getCandidateConttroller() {
        String key = CandidateController.class.getName();
        if (!this.services.containsKey(key)) {
            this.services.put(key, new CandidateController(getCandidateRepository(), getValidator(Candidate.class)));
        }

        return (CandidateController) this.services.get(key);
    }

    public DepartmentController getDepartmentController() {
        String key = DepartmentController.class.getName();
        if (!this.services.containsKey(key)) {
            this.services.put(key, new DepartmentController(getDepartmentRepository(), getValidator(Department.class)));
        }

        return (DepartmentController) this.services.get(key);
    }

    public OptionController getOptionController() {
        String key = OptionController.class.getName();
        if(!this.services.containsKey(key)) {
            this.services.put(key, new OptionController(getOptionRepository(), getCandidateRepository(), getDepartmentRepository(), getValidator(Option.class)));
        }

        return (OptionController) this.services.get(key);
    }

    private <T> FileSaverInterface<T> getSaver(Class<T> tClass) {
        if (this.services.containsKey(tClass.getName() + ".saver")){
            //noinspection unchecked
            return (FileSaverInterface<T>) this.services.get(tClass.getName() + ".saver");
        }

        throw new RuntimeException("Could not find a saver for entity " + tClass.getName());
    }

    private <T> FileLoaderInterface<T> getLoader(Class<T> tClass) {
        if (this.services.containsKey(tClass.getName() + ".loader")){
            //noinspection unchecked
            return (FileLoaderInterface<T>) this.services.get(tClass.getName() + ".loader");
        }

        throw new RuntimeException("Could not find a loader for entity " + tClass.getName());
    }

    private <Id, T extends HasId<Id>> MapperInterface<Id, T> getMapper(Class<Id> idClass, Class<T> entityClass) {
        String key = idClass.getName() + entityClass.getName() + ".mapper";

        if (this.services.containsKey(key)){
            //noinspection unchecked
            return (MapperInterface<Id, T>) this.services.get(key);
        }

        throw new RuntimeException("Could not find a loader for entity " + entityClass.getName());
    }

    private String getDatabaseConfig(String key) {
        return this.config.getDatabase().get(key);
    }

    private String getDomainConfig(String className, String key) {
        return this.config.getDomainFor(className).get(key);
    }

    private <Id, T extends HasId<Id>> RepositoryInterface<Id, T> getFileRepo(Class<Id> idClass, Class<T> entityClass) {
        String key = idClass.getName() + entityClass.getName() + ".com.ubb.map.repository";

        if (!services.containsKey(key)) {
            String filename = getDomainConfig(entityClass.getSimpleName(), "file");

            RepositoryInterface<Id, T> repo = new Repository<>();
            repo = new FileLoadingRepository<>(repo, getLoader(entityClass), filename);
            repo = new FileSavingRepository<>(repo, getSaver(entityClass), filename);

            services.put(key, repo);
        }

        //noinspection unchecked
        return (RepositoryInterface<Id, T>) services.get(key);
    }

    private ConnectionSource getConnection() {
        try {
            ConnectionSource connection = new JdbcConnectionSource(getDatabaseConfig("url"), getDatabaseConfig("user"), getDatabaseConfig("pass"));
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private <Id, T extends HasId<Id>> RepositoryInterface<Id, T> getDbRepo(Class<Id> idClass, Class<T> entityClass) throws Exception{
        String key = idClass.getName() + entityClass.getName() + ".com.ubb.map.repository";

        if (!services.containsKey(key)) {
            RepositoryInterface<Id, T> repo;
            ConnectionSource connection = null;
            try {
                connection = getConnection();
                repo = new OrmRepository<>(connection, entityClass);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }

            services.put(key, repo);
        }

        //noinspection unchecked
        return (RepositoryInterface<Id, T>) services.get(key);
    }

    private void registerDefaultValues() {

        String optionClass = Option.class.getName();
        String candidateClass = Candidate.class.getName();
        String departmentClass = Department.class.getName();

        services.putIfAbsent(optionClass + ".com.ubb.map.validator", new OptionValidator());
        services.putIfAbsent(candidateClass + ".com.ubb.map.validator", new CandidateValidator());
        services.putIfAbsent(departmentClass + ".com.ubb.map.validator", new DepartmentValidator());

        services.putIfAbsent(Integer.class.getName() + candidateClass + ".mapper", new CandidateMapper());
        services.putIfAbsent(String.class.getName() + departmentClass + ".mapper", new DepartmentMapper());
        services.putIfAbsent(Integer.class.getName() + optionClass + ".mapper", new OptionMapper(
                getCandidateRepository(),
                getDepartmentRepository()
        ));

//            services.putIfAbsent(optionClass + ".saver", new SerializedSaver<Option>());
//            services.putIfAbsent(candidateClass + ".saver", new SerializedSaver<Candidate>());
//            services.putIfAbsent(departmentClass + ".saver", new SerializedSaver<Department>());
//
//            services.putIfAbsent(candidateClass + ".loader", new SerializedLoader<Candidate>());
//            services.putIfAbsent(departmentClass + ".loader", new SerializedLoader<Department>());
//            services.putIfAbsent(optionClass + ".loader", new SerializedLoader<Option>());
    }
}
