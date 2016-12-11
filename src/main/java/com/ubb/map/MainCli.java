package com.ubb.map;

import com.ubb.map.services.OptionCrudService;
import com.ubb.map.di.producers.RepositoryProducer;
import com.ubb.map.menu.command.candidate.AddCandidateCommand;
import com.ubb.map.menu.command.candidate.DeleteCandidateCommand;
import com.ubb.map.menu.command.candidate.PrintCandidatesCommand;
import com.ubb.map.menu.command.candidate.UpdateCandidateCommand;
import com.ubb.map.menu.command.common.GoBackCommand;
import com.ubb.map.menu.command.department.AddDepartmentCommand;
import com.ubb.map.menu.command.department.DeleteDepartmentCommand;
import com.ubb.map.menu.command.department.PrintDepartmentsCommand;
import com.ubb.map.menu.command.department.UpdateDepartmentCommand;

import com.ubb.map.services.CandidateCrudService;
import com.ubb.map.services.DepartmentCrudService;

import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.Department;
import com.ubb.map.menu.command.option.AddOptionCommand;

import com.ubb.map.helper.PrintTableHelper;

import com.ubb.map.helper.loader.memory.CandidateMemoryLoader;
import com.ubb.map.helper.loader.memory.DepartmentMemoryLoader;
import com.ubb.map.helper.loader.memory.MemoryLoaderInterface;
import com.ubb.map.menu.command.option.DeleteOptionCommand;
import com.ubb.map.menu.command.option.PrintOptionsCommand;
import com.ubb.map.menu.command.option.UpdateOptionCommand;

import com.ubb.map.menu.Menu;
import com.ubb.map.view.decorator.IndentablePrintStream;

import java.sql.SQLException;
import java.util.Scanner;

public class MainCli {
    public static void main(String[] args) throws SQLException {

        RepositoryProducer container = new RepositoryProducer("src/main/resources/config/config.yml");

        CandidateCrudService candidateCrudService = container.getCandidateConttroller();
        DepartmentCrudService departmentCrudService = container.getDepartmentController();
        OptionCrudService optionCrudService = container.getOptionController();

        Menu menu            = new Menu("1", "Main com.ubb.map.menu");
        Menu candidatesMenu  = new Menu("1", "Candidates com.ubb.map.menu");
        Menu departmentsMenu = new Menu("2", "Departments com.ubb.map.menu");
        Menu optionMenu      = new Menu("3", "Options com.ubb.map.menu");

        menu.addItem(new GoBackCommand("0", "Exit the program...."));
        menu.addItem(candidatesMenu);
        menu.addItem(departmentsMenu);
        menu.addItem(optionMenu);

        PrintTableHelper helper = new PrintTableHelper(40, System.out);

        loadOptionsCommands(optionMenu, optionCrudService, helper);
        loadCandidatesCommands(candidatesMenu, candidateCrudService, helper);
        loadDepartmentsCommands(departmentsMenu, departmentCrudService, helper);

        MemoryLoaderInterface<Department> memDepLoader = new DepartmentMemoryLoader();
        MemoryLoaderInterface<Candidate> memCandLoader = new CandidateMemoryLoader();

        boolean loadFromMemory = false;
        if(loadFromMemory) {
            container.getDepartmentRepository().addCollection(memDepLoader.load(150));
            container.getCandidateRepository().addCollection(memCandLoader.load(150));
        }

        menu.execute(new Scanner(System.in), new IndentablePrintStream(System.out, 50));
    }

    private static void loadCandidatesCommands(Menu menu, CandidateCrudService controller, PrintTableHelper helper) {
        Menu crudMenu = new Menu("1", "Crud");
        menu.addItem(crudMenu);

        crudMenu.addItem(new AddCandidateCommand("1", "Add a new candidate", controller));
        crudMenu.addItem(new UpdateCandidateCommand("2", "Update a candidate", controller));
        crudMenu.addItem(new DeleteCandidateCommand("3", "Delete a candidate", controller));
        crudMenu.addItem(new GoBackCommand("0", "Go Back"));

        menu.addItem(new PrintCandidatesCommand("2", "Show all candidates", controller, helper));
        menu.addItem(new GoBackCommand("0", "Go Back"));
    }

    private static void loadDepartmentsCommands(Menu menu, DepartmentCrudService controller, PrintTableHelper helper) {
        Menu crudMenu = new Menu("1", "Crud");
        menu.addItem(crudMenu);

        crudMenu.addItem(new AddDepartmentCommand("1", "Add a new department", controller));
        crudMenu.addItem(new UpdateDepartmentCommand("2", "Update a department", controller));
        crudMenu.addItem(new DeleteDepartmentCommand("3", "Delete a department", controller));
        crudMenu.addItem(new GoBackCommand("0", "Go Back"));

        menu.addItem(new PrintDepartmentsCommand("2", "Show all departments", controller, helper));
        menu.addItem(new GoBackCommand("0", "Go Back"));
    }

    private static void loadOptionsCommands(Menu menu, OptionCrudService controller, PrintTableHelper helper) {
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