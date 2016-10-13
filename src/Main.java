import command.candidate.AddCandidateCommand;
import command.candidate.DeleteCandidateCommand;
import command.candidate.PrintCandidatesCommand;
import command.candidate.UpdateCandidateCommand;
import command.common.ExitCommand;
import command.department.AddDepartmentCommand;
import command.department.DeleteDepartmentCommand;
import command.department.PrintDepartmentsCommand;
import command.department.UpdateDepartmentCommand;
import controller.CandidateController;
import controller.DepartmentController;
import domain.Candidate;
import domain.Department;
import org.apache.commons.lang.RandomStringUtils;
import repository.CandidateRepository;
import repository.DepartmentRepository;
import repository.RepositoryInterface;
import util.helper.PrintTableHelper;
import validator.CandidateValidator;
import validator.DepartmentValidator;
import view.menu.MainMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CandidateRepository candidateRepository = new CandidateRepository();
        CandidateValidator candidateValidator = new CandidateValidator();
        CandidateController candidateController = new CandidateController(candidateRepository, candidateValidator);

        DepartmentRepository departmentRepository = new DepartmentRepository();
        DepartmentValidator departmentValidator = new DepartmentValidator();
        DepartmentController departmentController = new DepartmentController(departmentRepository, departmentValidator);

        MainMenu menu = new MainMenu(new Scanner(System.in), System.out);

        loadCommands(menu, candidateController, departmentController);
        loadCandidates(candidateRepository, 500);
        loadDepartments(departmentRepository, 500);

        menu.show();
    }

    private static void loadCommands(MainMenu menu, CandidateController candidateController, DepartmentController departmentController) {
        PrintTableHelper tableHelper = new PrintTableHelper(40, menu.getOut());

        menu.addCommand(new ExitCommand("0", "Exit"));

        menu.addCommand(new AddCandidateCommand   ("1", "Add a new candidate", candidateController));
        menu.addCommand(new UpdateCandidateCommand("2", "Update a candidate" , candidateController));
        menu.addCommand(new DeleteCandidateCommand("3", "Delete a candidate" , candidateController));
        menu.addCommand(new PrintCandidatesCommand("4", "Show all candidates", candidateController, tableHelper));

        menu.addCommand(new AddDepartmentCommand   ("5", "Add a new department", departmentController));
        menu.addCommand(new UpdateDepartmentCommand("6", "Update a department" , departmentController));
        menu.addCommand(new DeleteDepartmentCommand("7", "Delete a department" , departmentController));
        menu.addCommand(new PrintDepartmentsCommand("8", "Show all departments", departmentController, tableHelper));

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
