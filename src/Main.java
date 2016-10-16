import controller.OptionController;
import domain.Option;
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
import menu.command.option.AddOptionCommand;
import repository.*;

import repository.decorator.FileLoadingRepository;
import repository.decorator.FileSavingRepository;
import util.helper.PrintTableHelper;

import util.helper.loader.file.CandidateCsvLoader;
import util.helper.loader.file.DepartmentCsvLoader;
import util.helper.loader.file.OptionCsvLoader;
import util.helper.loader.memory.CandidateMemoryLoader;
import util.helper.loader.memory.DepartmentMemoryLoader;
import util.helper.loader.memory.MemoryLoaderInterface;
import util.helper.saver.CsvFileSaver;
import validator.CandidateValidator;
import validator.DepartmentValidator;

import menu.Menu;
import validator.OptionValidaor;
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

        String optionsFile = "options.txt";
        RepositoryInterface<Option> decoratedOptRepo =
                new FileLoadingRepository<>(
                        new FileSavingRepository<>(
                                new Repository<>(),
                                new CsvFileSaver<>(),
                                optionsFile
                        ),
                        new OptionCsvLoader<>(decoratedCandRepo, decoratedDepRepo),
                        optionsFile
                );
        OptionController optionController = new OptionController(decoratedOptRepo, decoratedCandRepo, decoratedDepRepo, new OptionValidaor());

        Menu menu = new Menu("1", "Main menu");
        Menu candidatesMenu  = new Menu("1", "Candidates menu");
        Menu departmentsMenu = new Menu("2", "Departments menu");
        Menu optionMenu      = new Menu("3", "Options menu");

        menu.addItem(new GoBackCommand("0", "Exit the program...."));
        menu.addItem(candidatesMenu);
        menu.addItem(departmentsMenu);
        menu.addItem(optionMenu);

        PrintTableHelper helper = new PrintTableHelper(40, System.out);

        loadOptionsCommands(optionMenu, optionController, helper);
        loadCandidatesCommands(candidatesMenu, candidateController, helper);
        loadDepartmentsCommands(departmentsMenu, departmentController, helper);

        MemoryLoaderInterface<Department> memDepLoader = new DepartmentMemoryLoader<>();
        MemoryLoaderInterface<Candidate> memCandLoader = new CandidateMemoryLoader<>();

//        memDepLoader.load(decoratedDepRepo, 10);
//        memCandLoader.load(decoratedCandRepo, 10);

        menu.execute(new Scanner(System.in), new IndentablePrintStream(System.out, 50));
    }

    private static void loadCandidatesCommands(Menu menu, CandidateController controller, PrintTableHelper helper) {
        Menu submenu1 = new Menu("1", "Crud");
        menu.addItem(submenu1);

        submenu1.addItem(new AddCandidateCommand("1", "Add a new candidate", controller));
        submenu1.addItem(new UpdateCandidateCommand("2", "Update a candidate", controller));
        submenu1.addItem(new DeleteCandidateCommand("3", "Delete a candidate", controller));
        submenu1.addItem(new GoBackCommand("0", "Go Back"));

        menu.addItem(new PrintCandidatesCommand("2", "Show all candidates", controller, helper));
        menu.addItem(new GoBackCommand("0", "Go Back"));
    }

    private static void loadDepartmentsCommands(Menu menu, DepartmentController controller, PrintTableHelper helper) {
        Menu submenu1 = new Menu("1", "Crud");
        menu.addItem(submenu1);

        submenu1.addItem(new AddDepartmentCommand("1", "Add a new department", controller));
        submenu1.addItem(new UpdateDepartmentCommand("2", "Update a department", controller));
        submenu1.addItem(new DeleteDepartmentCommand("3", "Delete a department", controller));
        submenu1.addItem(new GoBackCommand("0", "Go Back"));

        menu.addItem(new PrintDepartmentsCommand("2", "Show all departments", controller, helper));
        menu.addItem(new GoBackCommand("0", "Go Back"));
    }

    private static void loadOptionsCommands(Menu menu, OptionController controller, PrintTableHelper helper) {
        Menu submenu1 = new Menu("1", "Crud");
        menu.addItem(submenu1);

        submenu1.addItem(new AddOptionCommand("1", "Place a option for a candidate", controller));
        submenu1.addItem(new GoBackCommand("0", "Enemy spotted, fallback..."));

        menu.addItem(new GoBackCommand("0", "Return"));
    }
}