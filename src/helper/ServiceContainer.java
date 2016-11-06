package helper;

import domain.Candidate;
import domain.Department;
import domain.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import helper.loader.file.FileLoaderInterface;
import helper.loader.file.serialized.SerializedLoader;
import helper.saver.FileSaverInterface;
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
@SuppressWarnings("unchecked")
public class ServiceContainer {
    private Map<String, Object>  config;
    private Map<String, Boolean> locked;
    private Map<String, Object>  services;

    public ServiceContainer() {
        this.config   = new HashMap<>();
        this.locked   = new HashMap<>();
        this.services = new HashMap<>();

        this.registerDefaultValues();
    }

    private void registerDefaultValues() {
        try {
            setConfig("candidatesFile", "files/candidates_serializable.txt");
            setConfig("departmentsFile", "files/departments_serializable.txt");
            setConfig("optionsFile", "files/options_serializable.txt");
            setConfig("separator", " | ");

            services.putIfAbsent("option.validator", new OptionValidator());
            services.putIfAbsent("candidate.validator", new CandidateValidator());
            services.putIfAbsent("department.validator", new DepartmentValidator());

            services.putIfAbsent("option.saver", new SerializedSaver<Option>());
            services.putIfAbsent("candidate.saver", new SerializedSaver<Candidate>());
            services.putIfAbsent("department.saver", new SerializedSaver<Department>());

            services.putIfAbsent("option.loader", new SerializedLoader<Option>());
            services.putIfAbsent("candidate.loader", new SerializedLoader<Candidate>());
            services.putIfAbsent("department.loader", new SerializedLoader<Department>());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object getConfig(String key) throws NoSuchElementException {
        Object obj = config.get(key);

        if (obj == null) {
            throw new NoSuchElementException("Could not find config value " + key +".");
        }

        locked.put(key, true);
        return obj;
    }

    public void setConfig(String key, Object value) throws IllegalAccessException {
        if (locked.get(key) == null || !locked.get(key)) {
            config.put(key, value);
            return;
        }

        throw new IllegalAccessException("The config value with key " + key +" is locked and cannot ne modified");
    }

    private FileSaverInterface getSaver(Class tClass) {
        if (tClass == Candidate.class) {
            return (FileSaverInterface) services.get("candidate.saver");
        }

        if (tClass == Option.class) {
            return (FileSaverInterface) services.get("option.saver");
        }

        if (tClass == Department.class) {
            return (FileSaverInterface) services.get("department.saver");
        }

        throw new RuntimeException("Could not find a validator for entity " + tClass.getName());
    }

    private FileLoaderInterface getLoader(Class tClass) {
        if (tClass == Candidate.class) {
            return (FileLoaderInterface) services.get("candidate.loader");
        }

        if (tClass == Option.class) {
            return (FileLoaderInterface) services.get("option.loader");
        }

        if (tClass == Department.class) {
            return (FileLoaderInterface) services.get("department.loader");
        }

        throw new RuntimeException("Could not find a validator for entity " + tClass.getName());
    }

    public RepositoryInterface<Integer, Option> getOptionRepository() {
        if (services.get("option.repository") == null) {
            String optionsFile = (String) getConfig("optionsFile");

            RepositoryInterface<Integer, Option> optionRepo = new Repository<>();
            optionRepo = new FileLoadingRepository<>(optionRepo, getLoader(Option.class), optionsFile);
            optionRepo = new FileSavingRepository<>(optionRepo, getSaver(Option.class), optionsFile);

            services.put("option.repository", optionRepo);
        }

        return (RepositoryInterface<Integer, Option>) services.get("option.repository");
    }

    public RepositoryInterface<Integer, Candidate> getCandidateRepo() {
        if (services.get("candidate.repository") == null) {
            String candidatesFile = (String) getConfig("candidatesFile");

            RepositoryInterface<Integer, Candidate> candidateRepo = new Repository<>();
            candidateRepo = new FileSavingRepository<>(candidateRepo, getSaver(Candidate.class), candidatesFile);
            candidateRepo = new FileLoadingRepository<>(candidateRepo, getLoader(Candidate.class), candidatesFile);

            services.put("candidate.repository", candidateRepo);
        }

        return (RepositoryInterface<Integer, Candidate>) services.get("candidate.repository");
    }

    public RepositoryInterface<String, Department> getDepartmentRepo() {
        if (services.get("department.repository") == null) {
            String departmentsFile = (String) getConfig("departmentsFile");

            RepositoryInterface<String, Department> departmentRepo = new Repository<>();
            departmentRepo = new FileLoadingRepository<>(departmentRepo, getLoader(Department.class), departmentsFile);
            departmentRepo = new FileSavingRepository<>(departmentRepo, getSaver(Department.class), departmentsFile);

            services.put("department.repository", departmentRepo);
        }

        return (RepositoryInterface<String, Department>) services.get("department.repository");
    }

    public ValidatorInterface getValidator(Class tClass) {
        if (tClass == Candidate.class) {
            return (ValidatorInterface) services.get("candidate.validator");
        }

        if (tClass == Option.class) {
            return (ValidatorInterface) services.get("option.validator");
        }

        if (tClass == Department.class) {
            return (ValidatorInterface) services.get("department.validator");
        }

        throw new RuntimeException("Could not find a validator for entity " + tClass.getName());
    }
}
