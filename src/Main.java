import controller.CandidateController;
import controller.DepartmentController;
import domain.Candidate;
import domain.Department;
import org.apache.commons.lang.RandomStringUtils;
import repository.CandidateRepository;
import repository.DepartmentRepository;
import repository.Repository;
import repository.RepositoryInterface;
import util.UbbArray;
import validator.CandidateValidator;
import validator.DepartmentValidator;
import view.Console;

public class Main {

    public static void main(String[] args) {
        CandidateRepository candidateRepository = new CandidateRepository();
        CandidateValidator candidateValidator = new CandidateValidator();
        CandidateController candidateController = new CandidateController(candidateRepository, candidateValidator);

        DepartmentRepository departmentRepository = new DepartmentRepository();
        DepartmentValidator departmentValidator = new DepartmentValidator();
        DepartmentController departmentController = new DepartmentController(departmentRepository, departmentValidator);

        Console console = new Console(candidateController, departmentController);

        loadCandidates(candidateRepository, 5);
        loadDepartments(departmentRepository, 5);
        console.run();
    }

    private static void loadCandidates(RepositoryInterface repository, Integer howMany) {
        for(int i = 0; i < howMany; i++) {
            repository.insert(new Candidate(
                    repository.getNextId(),
                    "Candidate" + i,
                    RandomStringUtils.randomNumeric(10),
                    "Street " + RandomStringUtils.randomAlphabetic(10) +
                            ", nr " + RandomStringUtils.randomNumeric(3) +
                            ", City " + RandomStringUtils.randomAlphabetic(6)
            ));
        }
    }

    private static void loadDepartments(RepositoryInterface repository, Integer howMany) {
        for(int i = 0; i < howMany; i++) {
            repository.insert(new Department(
                    repository.getNextId(),
                    "Department" + i,
                    Integer.valueOf(RandomStringUtils.randomNumeric(3))
            ));
        }
    }
}
