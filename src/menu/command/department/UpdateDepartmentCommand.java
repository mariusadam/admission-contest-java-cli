package menu.command.department;

import controller.DepartmentController;
import domain.Department;
import exception.InvalidDepartmentException;
import exception.InvalidEntityException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class UpdateDepartmentCommand extends BaseDepartmentCommand {
    /**
     * @param key                  The key which identifies the menu.command
     * @param text                 Short description for the menu.command
     * @param departmentController The controller which handles departments
     */
    public UpdateDepartmentCommand(String key, String text, DepartmentController departmentController) {
        super(key, text, departmentController);
    }

    /**
     * Executes the current menu.command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        Integer id;
        String name;
        Integer numberOfSeats;

        out.print("Enter the id of the department you with to modify: ");
        id = scanner.nextInt();
        scanner.nextLine();
        out.print("Enter the new phone of the department or leave id blank to not change it: ");
        name = scanner.nextLine();
        out.print("Enter the number of seats of the department or leave id blank to not change it: ");
        numberOfSeats = scanner.nextInt();

        try {
            Department department = this.departmentController.update(id, name, numberOfSeats);
            out.println("Updated " + department);
        } catch (InvalidEntityException e) {
            e.printStackTrace(out);
        }
    }
}
