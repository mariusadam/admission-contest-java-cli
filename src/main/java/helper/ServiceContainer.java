package helper;

import controller.CandidateController;
import controller.DepartmentController;
import controller.OptionController;
import domain.Candidate;
import domain.Department;
import domain.HasId;
import domain.Option;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import helper.config.Configuration;
import helper.loader.file.FileLoaderInterface;
import helper.mapper.CandidateMapper;
import helper.mapper.DepartmentMapper;
import helper.mapper.MapperInterface;
import helper.mapper.OptionMapper;
import helper.saver.FileSaverInterface;
import repository.Repository;
import repository.RepositoryInterface;
import repository.db.DbRepository;
import repository.decorator.FileLoadingRepository;
import repository.decorator.FileSavingRepository;
import validator.CandidateValidator;
import validator.DepartmentValidator;
import validator.OptionValidator;
import validator.ValidatorInterface;

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
        return this.getDbRepo(Integer.class, Option.class);
    }

    public RepositoryInterface<Integer, Candidate> getCandidateRepository() {
        return this.getDbRepo(Integer.class, Candidate.class);
    }

    public RepositoryInterface<String, Department> getDepartmentRepository() {
        return this.getDbRepo(String.class, Department.class);
    }

    public <T> ValidatorInterface<T> getValidator(Class<T> tClass) {
        if (this.services.containsKey(tClass.getName() + ".validator")){
            //noinspection unchecked
            return (ValidatorInterface<T>) this.services.get(tClass.getName() + ".validator");
        }

        throw new RuntimeException("Could not find a validator for entity " + tClass.getName());
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
        String key = idClass.getName() + entityClass.getName() + ".repository";

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

    private Connection getConnection() {
        String key = "db.connection";

        if (!services.containsKey(key)) {
            try {
                Connection connection = DriverManager.getConnection(getDatabaseConfig("url"), getDatabaseConfig("user"), getDatabaseConfig("pass"));

                services.put(key, connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return (Connection) services.get(key);
    }

    private <Id, T extends HasId<Id>> RepositoryInterface<Id, T> getDbRepo(Class<Id> idClass, Class<T> entityClass) {
        String key = idClass.getName() + entityClass.getName() + ".repository";

        if (!services.containsKey(key)) {
            String table = getDomainConfig(entityClass.getSimpleName(), "table");
            RepositoryInterface<Id, T> repo = new DbRepository<>(getConnection(), getMapper(idClass, entityClass), table);

            services.put(key, repo);
        }

        //noinspection unchecked
        return (RepositoryInterface<Id, T>) services.get(key);
    }

    private void registerDefaultValues() {

        String optionClass = Option.class.getName();
        String candidateClass = Candidate.class.getName();
        String departmentClass = Department.class.getName();

        services.putIfAbsent(optionClass + ".validator", new OptionValidator());
        services.putIfAbsent(candidateClass + ".validator", new CandidateValidator());
        services.putIfAbsent(departmentClass + ".validator", new DepartmentValidator());

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
