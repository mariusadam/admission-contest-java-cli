import menu.MenuItemInterface;
import menu.command.candidate.AddCandidateCommand;
import menu.command.candidate.DeleteCandidateCommand;
import menu.command.candidate.PrintCandidatesCommand;
import menu.command.candidate.UpdateCandidateCommand;
import menu.command.common.ExitCommand;
import menu.command.common.GoBackCommand;
import menu.command.department.AddDepartmentCommand;
import menu.command.department.DeleteDepartmentCommand;
import menu.command.department.PrintDepartmentsCommand;
import menu.command.department.UpdateDepartmentCommand;

import controller.CandidateController;
import controller.DepartmentController;

import domain.Candidate;
import domain.Department;
import repository.*;

import repository.decorator.FileLoadingRepository;
import repository.decorator.FileSavingRepository;
import util.helper.PrintTableHelper;

import util.helper.loader.file.CandidateCsvLoader;
import util.helper.loader.file.DepartmentCsvLoader;
import util.helper.loader.memory.CandidateMemoryLoader;
import util.helper.loader.memory.DepartmentMemoryLoader;
import util.helper.loader.memory.MemoryLoaderInterface;
import util.helper.saver.CsvFileSaver;
import validator.CandidateValidator;
import validator.DepartmentValidator;

import menu.Menu;
import view.decorator.IndentablePrintStream;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String canidatesFile = "candidates.txt";
        RepositoryInterface<Candidate> decoratedCandRepo =
                new FileSavingRepository<>(
                        new FileLoadingRepository<>(
                                new Repository<>(),
                                new CandidateCsvLoader<>(),
                                canidatesFile
                        ),
                        new CsvFileSaver<>(),
                        canidatesFile
                );
        CandidateController candidateController = new CandidateController(decoratedCandRepo, new CandidateValidator());

        String departmentsFile = "departments.txt";
        RepositoryInterface<Department> decoratedDepRepo =
                new FileLoadingRepository<>(
                        new FileSavingRepository<>(
                                new Repository<>(),
                                new CsvFileSaver<>(),
                                departmentsFile
                        ),
                        new DepartmentCsvLoader<>(),
                        departmentsFile
                );
        DepartmentController departmentController = new DepartmentController(decoratedDepRepo, new DepartmentValidator());

        Menu menu = new Menu("1", "Main menu");
        loadCommands(menu, candidateController, departmentController);

        MemoryLoaderInterface<Department> memDepLoader = new DepartmentMemoryLoader<>();
        MemoryLoaderInterface<Candidate> memCandLoader = new CandidateMemoryLoader<>();

//        memDepLoader.load(decoratedDepRepo, 10);
//        memCandLoader.load(decoratedCandRepo, 10);

        menu.execute(new Scanner(System.in), new IndentablePrintStream(System.out, 50));
    }

    private static void loadCommands(Menu menu, CandidateController candidateController, DepartmentController departmentController) {
        PrintTableHelper tableHelper = new PrintTableHelper(40, System.out);

        Menu submenu1 = new Menu("1", "Candidates menu");
        Menu submenu2 = new Menu("2", "Departments menu");

        menu.addItem(submenu1);
        menu.addItem(submenu2);
        menu.addItem(new ExitCommand("0", "Exit..."));

        Menu submenu11 = new Menu("1", "Crud");
        Menu submenu21 = new Menu("1", "Crud");
        submenu1.addItem(submenu11);
        submenu2.addItem(submenu21);

        submenu11.addItem(new AddCandidateCommand("1", "Add a new candidate", candidateController));
        submenu11.addItem(new UpdateCandidateCommand("2", "Update a candidate", candidateController));
        submenu11.addItem(new DeleteCandidateCommand("3", "Delete a candidate", candidateController));
        submenu11.addItem(new GoBackCommand("0", "Go Back"));

        submenu1.addItem(new PrintCandidatesCommand("2", "Show all candidates", candidateController, tableHelper));
        submenu1.addItem(new GoBackCommand("0", "Go Back"));

        submenu21.addItem(new AddDepartmentCommand("1", "Add a new department", departmentController));
        submenu21.addItem(new UpdateDepartmentCommand("2", "Update a department", departmentController));
        submenu21.addItem(new DeleteDepartmentCommand("3", "Delete a department", departmentController));
        submenu21.addItem(new GoBackCommand("0", "Go Back"));

        submenu2.addItem(new PrintDepartmentsCommand("2", "Show all departments", departmentController, tableHelper));
        submenu2.addItem(new GoBackCommand("0", "Go Back"));
    }
}