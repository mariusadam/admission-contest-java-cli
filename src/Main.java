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

import repository.CandidateRepository;
import repository.DepartmentRepository;

import util.helper.PrintTableHelper;
import util.helper.loader.CandidateLoader;
import util.helper.loader.DepartmentLoader;
import util.helper.loader.LoaderInterface;

import validator.CandidateValidator;
import validator.DepartmentValidator;

import view.menu.Menu;
import view.menu.MenuInterface;
import view.menu.decorator.PrintStreamDecorator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CandidateRepository candidateRepository = new CandidateRepository();
        CandidateController candidateController = new CandidateController(candidateRepository, new CandidateValidator());

        DepartmentRepository departmentRepository = new DepartmentRepository();
        DepartmentController departmentController = new DepartmentController(departmentRepository, new DepartmentValidator());

        MenuInterface menu = new Menu(new Scanner(System.in), new PrintStreamDecorator(System.out, 40));

        loadCommands(menu, candidateController, departmentController);

        LoaderInterface candidateLoader  = new CandidateLoader();
        LoaderInterface departmentLoader = new DepartmentLoader();

        candidateLoader.load(candidateRepository, 10);
        departmentLoader.load(departmentRepository, 10);

        menu.show();
    }

    private static void loadCommands(MenuInterface menu, CandidateController candidateController, DepartmentController departmentController) {
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
}
