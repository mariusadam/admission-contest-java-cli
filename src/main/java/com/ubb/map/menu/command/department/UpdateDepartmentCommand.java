package com.ubb.map.menu.command.department;

import com.ubb.map.controller.DepartmentController;
import com.ubb.map.domain.Department;
import com.ubb.map.exception.InvalidObjectException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Marius Adam.
 */
public class UpdateDepartmentCommand extends BaseDepartmentCommand {
    /**
     * @param key                  The key which identifies the com.ubb.map.menu.command
     * @param text                 Short description for the com.ubb.map.menu.command
     * @param departmentController The com.ubb.map.controller which handles departments
     */
    public UpdateDepartmentCommand(String key, String text, DepartmentController departmentController) {
        super(key, text, departmentController);
    }

    /**
     * Executes the current com.ubb.map.menu.command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        String id;
        String name;
        Integer numberOfSeats;

        out.print("Enter the id of the department you with to modify: ");
        id = scanner.nextLine();
        scanner.nextLine();
        out.print("Enter the new phone of the department or leave id blank to not change it: ");
        name = scanner.nextLine();
        out.print("Enter the number of seats of the department or leave id blank to not change it: ");
        numberOfSeats = scanner.nextInt();

        Department department = null;
        try {
            department = this.departmentController.update(id, name, numberOfSeats);
            out.println("Updated " + department);
        } catch (InvalidObjectException e) {
            out.println(e.getMessage());
        }
    }
}
