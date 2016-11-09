package helper;

import domain.Candidate;
import domain.Department;
import domain.HasId;
import domain.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import helper.loader.file.FileLoaderInterface;
import helper.loader.file.json.CandidateJsonLoader;
import helper.loader.file.json.DepartmentJsonLoader;
import helper.loader.file.json.OptionJsonLoader;
import helper.loader.file.serialized.SerializedLoader;
import helper.saver.FileSaverInterface;
import helper.saver.JsonSaver;
import helper.saver.SerializedSaver;
import repository.Repository;
import repository.RepositoryInterface;
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
    private Map<String, String>  config;
    private Map<String, Boolean> locked;
    private Map<String, Object>  services;

    public ServiceContainer() {
        this.config   = new HashMap<>();
        this.locked   = new HashMap<>();
        this.services = new HashMap<>();

        this.registerDefaultValues();
    }

    public RepositoryInterface<Integer, Option> getOptionRepository() {
        return this.getRepo(Integer.class, Option.class);
    }

    public RepositoryInterface<Integer, Candidate> getCandidateRepository() {
        return this.getRepo(Integer.class, Candidate.class);
    }

    public RepositoryInterface<String, Department> getDepartmentRepository() {
        return this.getRepo(String.class, Department.class);
    }

    public <T> ValidatorInterface<T> getValidator(Class<T> tClass) {
        if (this.services.containsKey(tClass.getName() + ".validator")){
            //noinspection unchecked
            return (ValidatorInterface<T>) this.services.get(tClass.getName() + ".validator");
        }

        throw new RuntimeException("Could not find a validator for entity " + tClass.getName());
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

    private String getConfig(String key) throws NoSuchElementException {
        String obj = config.get(key);

        if (obj == null) {
            throw new NoSuchElementException("Could not find config value " + key +".");
        }

        locked.put(key, true);
        return obj;
    }

    private void setConfig(String key, String value) throws IllegalAccessException {
        if (locked.get(key) == null || !locked.get(key)) {
            config.put(key, value);
            return;
        }

        throw new IllegalAccessException("The config value with key " + key + " is locked and cannot ne modified");
    }

    private <Id, T extends HasId<Id>> RepositoryInterface<Id, T> getRepo(Class<Id> idClass, Class<T> entityClass) {
        String key = idClass.getName() + entityClass.getName() + ".repository";

        if (!services.containsKey(key)) {
            String filename = getConfig(entityClass.getName() + ".file");

            RepositoryInterface<Id, T> repo = new Repository<>();
            repo = new FileLoadingRepository<>(repo, getLoader(entityClass), filename);
            repo = new FileSavingRepository<>(repo, getSaver(entityClass), filename);

            services.put(key, repo);
        }

        //noinspection unchecked
        return (RepositoryInterface<Id, T>) services.get(key);
    }

    private void registerDefaultValues() {
        try {

            String optionClass = Option.class.getName();
            String candidateClass = Candidate.class.getName();
            String departmentClass = Department.class.getName();

            setConfig(optionClass + ".file", "files/options_serializable.txt");
            setConfig(candidateClass + ".file", "files/candidates_serializable.txt");
            setConfig(departmentClass + ".file", "files/departments_serializable.txt");
            setConfig("separator", " | ");

            services.putIfAbsent(optionClass + ".validator", new OptionValidator());
            services.putIfAbsent(candidateClass + ".validator", new CandidateValidator());
            services.putIfAbsent(departmentClass + ".validator", new DepartmentValidator());

            services.putIfAbsent(optionClass + ".saver", new SerializedSaver<Option>());
            services.putIfAbsent(candidateClass + ".saver", new SerializedSaver<Candidate>());
            services.putIfAbsent(departmentClass + ".saver", new SerializedSaver<Department>());

            services.putIfAbsent(candidateClass + ".loader", new SerializedLoader<Candidate>());
            services.putIfAbsent(departmentClass + ".loader", new SerializedLoader<Department>());
            services.putIfAbsent(optionClass + ".loader", new SerializedLoader<Option>());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
