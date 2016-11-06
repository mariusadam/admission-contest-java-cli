package helper;

import domain.Candidate;
import domain.Department;
import domain.Entity;
import domain.Option;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import helper.loader.file.csv.CandidateLoader;
import helper.loader.file.csv.DepartmentLoader;
import helper.loader.file.csv.OptionLoader;
import helper.saver.CsvFileSaver;
import helper.saver.FileSaverInterface;
import repository.Repository;
import repository.RepositoryInterface;
import repository.decorator.FileLoadingRepository;
import repository.decorator.FileSavingRepository;
import repository.decorator.RepositoryDecorator;
import repository.decorator.SerializableRepository;
import validator.CandidateValidator;
import validator.DepartmentValidator;
import validator.OptionValidator;
import validator.ValidatorInterface;

/**
 * @author Marius Adam
 */
@SuppressWarnings("unchecked")
public class Bootstrap {
    private Map<String, Object>  config;
    private Map<String, Boolean> locked;
    private Map<String, Object>  services;

    public Bootstrap() {
        this.config   = new HashMap<>();
        this.locked   = new HashMap<>();
        this.services = new HashMap<>();

        this.registerDefaultValues();
    }

    private void registerDefaultValues() {
        try {
            setConfig("candidatesFile", "candidates_serializable.txt");
            setConfig("departmentsFile", "departments_serializable.txt");
            setConfig("optionsFile", "options_serializable.txt");
            setConfig("separator", " | ");

            services.putIfAbsent("option.validator", new OptionValidator());
            services.putIfAbsent("candidate.validator", new CandidateValidator());
            services.putIfAbsent("department.validator", new DepartmentValidator());
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

    private FileSaverInterface getSaver() {
        if (services.get("saver") != null) {
            return (FileSaverInterface) services.get("saver");
        }
        services.put("saver", new CsvFileSaver<Entity>((String) config.get("separator")));

        return (FileSaverInterface) services.get("saver");
    }

    public RepositoryInterface<Integer, Option> getOptionRepository() {
        if (services.get("option.repository") == null) {
            String optionsFile = (String) getConfig("optionsFile");
            OptionValidator ov = (OptionValidator) this.getValidator(Option.class);
            OptionLoader optionLoader = new OptionLoader(ov, getCandidateRepo(), getDepartmentRepo());
            RepositoryInterface<Integer, Option> optionRepo = new Repository<>();
            optionRepo = new SerializableRepository<>(optionRepo, optionsFile);

//            optionRepo = new FileLoadingRepository<>(optionRepo, optionLoader, optionsFile);
//            optionRepo = new FileSavingRepository<>(optionRepo, getSaver(), optionsFile);

            services.put("option.repository", optionRepo);
        }

        return (RepositoryInterface<Integer, Option>) services.get("option.repository");
    }

    public RepositoryInterface<Integer, Candidate> getCandidateRepo() {
        if (services.get("candidate.repository") == null) {
            String candidatesFile = (String) getConfig("candidatesFile");
            CandidateValidator cv = (CandidateValidator) this.getValidator(Candidate.class);
            RepositoryInterface<Integer, Candidate> candidateRepo = new Repository<>();
            candidateRepo = new SerializableRepository<>(candidateRepo, candidatesFile);

//            FileSaverInterface<Candidate> ss = (FileSaverInterface<Candidate>) getSaver();
//            candidateRepo = new FileSavingRepository<>(candidateRepo, ss, candidatesFile);
//            candidateRepo = new FileLoadingRepository<>(candidateRepo, new CandidateLoader(cv), candidatesFile);

            services.put("candidate.repository", candidateRepo);
        }

        return (RepositoryInterface<Integer, Candidate>) services.get("candidate.repository");
    }

    public RepositoryInterface<String, Department> getDepartmentRepo() {
        if (services.get("department.repository") == null) {
            String departmentsFile = (String) getConfig("departmentsFile");
            DepartmentValidator dv = (DepartmentValidator) this.getValidator(Department.class);
            RepositoryInterface<String, Department> departmentRepo = new Repository<>();
            departmentRepo = new SerializableRepository<>(departmentRepo, departmentsFile);
//            departmentRepo         = new FileLoadingRepository<>(departmentRepo, new DepartmentLoader(dv), departmentsFile);
//            departmentRepo         = new FileSavingRepository<>(departmentRepo, getSaver(), departmentsFile);
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
