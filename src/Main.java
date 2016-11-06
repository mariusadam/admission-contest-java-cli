import controller.OptionController;
import domain.Option;
import helper.Bootstrap;
import menu.command.candidate.AddCandidateCommand;
import menu.command.candidate.DeleteCandidateCommand;
import menu.command.candidate.PrintCandidatesCommand;
import menu.command.candidate.UpdateCandidateCommand;
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

import helper.PrintTableHelper;

import helper.loader.memory.CandidateMemoryLoader;
import helper.loader.memory.DepartmentMemoryLoader;
import helper.loader.memory.MemoryLoaderInterface;
import menu.command.option.DeleteOptionCommand;
import menu.command.option.PrintOptionsCommand;
import menu.command.option.UpdateOptionCommand;

import menu.Menu;
import view.decorator.IndentablePrintStream;

import java.util.Scanner;

@SuppressWarnings("unchecked")
public class Main {

    public static void main(String[] args) {

        Bootstrap boot = new Bootstrap();

        CandidateController candidateController   = new CandidateController(boot.getCandidateRepo(), boot.getValidator(Candidate.class));
        DepartmentController departmentController = new DepartmentController(boot.getDepartmentRepo(), boot.getValidator(Department.class));
        OptionController optionController         = new OptionController(
                boot.getOptionRepository(),
                boot.getCandidateRepo(),
                boot.getDepartmentRepo(),
                boot.getValidator(Option.class)
        );

        Menu menu            = new Menu("1", "Main menu");
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

        MemoryLoaderInterface<Department> memDepLoader = new DepartmentMemoryLoader();
        MemoryLoaderInterface<Candidate> memCandLoader = new CandidateMemoryLoader();

        boolean loadFromMemory = false;
        if(loadFromMemory) {
            boot.getDepartmentRepo().addCollection(memDepLoader.load(15));
            boot.getCandidateRepo().addCollection(memCandLoader.load(15));
        }

        menu.execute(new Scanner(System.in), new IndentablePrintStream(System.out, 50));
    }

    private static void loadCandidatesCommands(Menu menu, CandidateController controller, PrintTableHelper helper) {
        Menu crudMenu = new Menu("1", "Crud");
        menu.addItem(crudMenu);

        crudMenu.addItem(new AddCandidateCommand("1", "Add a new candidate", controller));
        crudMenu.addItem(new UpdateCandidateCommand("2", "Update a candidate", controller));
        crudMenu.addItem(new DeleteCandidateCommand("3", "Delete a candidate", controller));
        crudMenu.addItem(new GoBackCommand("0", "Go Back"));

        menu.addItem(new PrintCandidatesCommand("2", "Show all candidates", controller, helper));
        menu.addItem(new GoBackCommand("0", "Go Back"));
    }

    private static void loadDepartmentsCommands(Menu menu, DepartmentController controller, PrintTableHelper helper) {
        Menu crudMenu = new Menu("1", "Crud");
        menu.addItem(crudMenu);

        crudMenu.addItem(new AddDepartmentCommand("1", "Add a new department", controller));
        crudMenu.addItem(new UpdateDepartmentCommand("2", "Update a department", controller));
        crudMenu.addItem(new DeleteDepartmentCommand("3", "Delete a department", controller));
        crudMenu.addItem(new GoBackCommand("0", "Go Back"));

        menu.addItem(new PrintDepartmentsCommand("2", "Show all departments", controller, helper));
        menu.addItem(new GoBackCommand("0", "Go Back"));
    }

    private static void loadOptionsCommands(Menu menu, OptionController controller, PrintTableHelper helper) {
        Menu crudMenu = new Menu("1", "Crud");
        menu.addItem(crudMenu);

        crudMenu.addItem(new AddOptionCommand("1", "Place a option for a candidate", controller));
        crudMenu.addItem(new UpdateOptionCommand("2", "Update an option", controller));
        crudMenu.addItem(new DeleteOptionCommand("3", "Delete an option", controller));
        crudMenu.addItem(new GoBackCommand("0", "Enemy spotted, fallback..."));

        menu.addItem(new PrintOptionsCommand("2", "Show all options", controller, helper));
        menu.addItem(new GoBackCommand("0", "Return"));
    }
}