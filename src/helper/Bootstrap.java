package helper;

import domain.Candidate;
import domain.Department;
import domain.Option;
import helper.loader.file.csv.CandidateLoader;
import helper.loader.file.csv.DepartmentLoader;
import helper.loader.file.csv.OptionLoader;
import helper.loader.file.json.CandidateJsonLoader;
import helper.loader.file.json.OptionJsonLoader;
import helper.saver.FileSaver;
import helper.saver.FileSaverInterface;
import helper.saver.JsonSaver;
import repository.Repository;
import repository.RepositoryInterface;
import repository.decorator.FileLoadingRepository;
import repository.decorator.FileSavingRepository;
import validator.CandidateValidator;
import validator.DepartmentValidator;
import validator.OptionValidator;

/**
 * @author Marius Adam
 */
public class Bootstrap {
    private static RepositoryInterface<Option>     optionRepo;
    private static RepositoryInterface<Candidate>  candidateRepo;
    private static RepositoryInterface<Department> departmentRepo;
    private static String separator = " | ";
    private static FileSaverInterface saver;




    private Bootstrap() {
    }

    private static FileSaverInterface getSaver() {
        if(saver == null) {
            saver = new JsonSaver();
        }

        return saver;
    }

    public static RepositoryInterface<Option> getOptionRepository() {
        if (optionRepo == null) {
            String optionsFile = "options.txt";
            OptionValidator ov = new OptionValidator();
            OptionJsonLoader optionLoader = new OptionJsonLoader(ov,getCandidateRepo(), getDepartmentRepo());
            optionRepo = new Repository<>();
            optionRepo = new FileLoadingRepository<>(optionRepo, optionLoader, optionsFile);
            optionRepo = new FileSavingRepository<>(optionRepo, getSaver(), optionsFile);
        }

        return optionRepo;
    }

    public static RepositoryInterface<Candidate> getCandidateRepo() {
        if (candidateRepo == null) {
            String candidatesFile = "candidates.txt";
            CandidateValidator cv = new CandidateValidator();
            candidateRepo = new Repository<>();
            candidateRepo = new FileSavingRepository<>(candidateRepo, getSaver(), candidatesFile);
            candidateRepo = new FileLoadingRepository<>(candidateRepo, new CandidateJsonLoader(cv), candidatesFile);
        }

        return candidateRepo;
    }

    public static RepositoryInterface<Department> getDepartmentRepo() {
        if (departmentRepo == null) {
            String departmentsFile = "departments.txt";
            DepartmentValidator dv = new DepartmentValidator();
            departmentRepo         = new Repository<>();
            departmentRepo         = new FileLoadingRepository<>(departmentRepo, new DepartmentLoader(dv, separator), departmentsFile);
            departmentRepo         = new FileSavingRepository<>(departmentRepo, getSaver(), departmentsFile);
        }

        return departmentRepo;
    }

    private static FormatterInterface getFormatter() {
        if (formatter == null) {
            formatter = new JsonFormatter(separator);
        }

        return formatter;
    }
}
