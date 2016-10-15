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
import util.helper.saver.SaverInterface;
import validator.CandidateValidator;
import validator.DepartmentValidator;

import view.menu.Menu;
import view.menu.MenuInterface;
import view.decorator.IndentablePrintStream;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SaverInterface csvSaver = new CsvFileSaver();

        String canidatesFile = "candidates.txt";
        RepositoryInterface<Candidate> decoratedCandRepo =
                new FileSavingRepository<>(
                        new FileLoadingRepository<>(
                                new Repository<>(),
                                new CandidateCsvLoader<>(),
                                canidatesFile
                        ),
                        csvSaver,
                        canidatesFile
                );
        CandidateController candidateController = new CandidateController(decoratedCandRepo, new CandidateValidator());

        String departmentsFile = "departments.txt";
        RepositoryInterface<Department> decoratedDepRepo =
                new FileLoadingRepository<>(
                        new FileSavingRepository<>(
                                new Repository<>(),
                                csvSaver,
                                departmentsFile
                        ),
                        new DepartmentCsvLoader<>(),
                        departmentsFile
                );
        DepartmentController departmentController = new DepartmentController(decoratedDepRepo, new DepartmentValidator());

        MenuInterface menu = new Menu(new Scanner(System.in), new IndentablePrintStream(System.out, 40));
        loadCommands(menu, candidateController, departmentController);

        MemoryLoaderInterface<Department> memDepLoader = new DepartmentMemoryLoader<>();
        MemoryLoaderInterface<Candidate> memCandLoader = new CandidateMemoryLoader<>();

//        memDepLoader.load(decoratedDepRepo, 1000);
//        memCandLoader.load(decoratedCandRepo, 1000);

        menu.show();
    }

    private static void loadCommands(MenuInterface menu, CandidateController candidateController, DepartmentController departmentController) {
        PrintTableHelper tableHelper = new PrintTableHelper(40, System.out);

        menu.addCommand(new ExitCommand("0", "Exit"));

        menu.addCommand(new AddCandidateCommand("1", "Add a new candidate", candidateController));
        menu.addCommand(new UpdateCandidateCommand("2", "Update a candidate", candidateController));
        menu.addCommand(new DeleteCandidateCommand("3", "Delete a candidate", candidateController));
        menu.addCommand(new PrintCandidatesCommand("4", "Show all candidates", candidateController, tableHelper));

        menu.addCommand(new AddDepartmentCommand("5", "Add a new department", departmentController));
        menu.addCommand(new UpdateDepartmentCommand("6", "Update a department", departmentController));
        menu.addCommand(new DeleteDepartmentCommand("7", "Delete a department", departmentController));
        menu.addCommand(new PrintDepartmentsCommand("8", "Show all departments", departmentController, tableHelper));

    }
}