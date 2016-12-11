package com.ubb.map.menu.command.department;

import com.ubb.map.services.DepartmentCrudService;
import com.ubb.map.domain.Department;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class AddDepartmentCommand extends BaseDepartmentCommand {

    /**
     * @param key                  The key which identifies the com.ubb.map.menu.command
     * @param text                 Short description for the com.ubb.map.menu.command
     * @param departmentCrudService The com.ubb.map.services which handles departments
     */
    public AddDepartmentCommand(String key, String text, DepartmentCrudService departmentCrudService) {
        super(key, text, departmentCrudService);
    }

    /**
     * Executes the current com.ubb.map.menu.command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        String name;
        Integer numberOfSeats;

        out.print("Enter the nae of the department: ");
        name = scanner.nextLine();
        out.print("Enter the number of seats of the department: ");
        numberOfSeats = scanner.nextInt();
        scanner.nextLine();

        try {
            Department department = this.departmentCrudService.create(name, numberOfSeats);
            out.println("Updated " + department);
        } catch (InvalidObjectException | DuplicateEntryException e) {
            out.println(e.getMessage());;
        }
    }
}
