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

import repository.*;

import repository.decorator.CandidateRepositoryDecorator;
import repository.decorator.DepartmentFileRepositoryDecorator;
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
        CandidateRepositoryInterface decoratedandRepo =
                new CandidateRepositoryDecorator(
                        new FileSavingRepository(
                                new FileLoadingRepository(
                                        new CandidateRepository(),
                                        new CandidateCsvLoader(),
                                        canidatesFile
                                ),
                                csvSaver,
                                canidatesFile
                        )
                );
        CandidateController candidateController = new CandidateController(decoratedandRepo, new CandidateValidator());

        String departmentsFile = "departments.txt";
        DepartmentRepositoryInterface decoratedDepRepo =
                new DepartmentFileRepositoryDecorator(
                        new FileLoadingRepository(
                                new FileSavingRepository(
                                        new DepartmentRepository(),
                                        csvSaver,
                                        departmentsFile
                                ),
                                new DepartmentCsvLoader(),
                                departmentsFile
                        )
                );
        DepartmentController departmentController = new DepartmentController(decoratedDepRepo, new DepartmentValidator());

        MenuInterface menu = new Menu(new Scanner(System.in), new IndentablePrintStream(System.out, 40));
        loadCommands(menu, candidateController, departmentController);

        MemoryLoaderInterface memDepLoader = new DepartmentMemoryLoader();
        MemoryLoaderInterface memCandLoader = new CandidateMemoryLoader();

        memDepLoader.load(decoratedDepRepo, 10);
        memCandLoader.load(decoratedandRepo, 10);

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