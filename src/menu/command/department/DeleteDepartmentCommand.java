package menu.command.department;

import controller.DepartmentController;
import domain.Department;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class DeleteDepartmentCommand extends AbstractDepartmentCommand{
    /**
     * @param key                  The key which identifies the menu.command
     * @param text                 Short description for the menu.command
     * @param departmentController The controller which handles departments
     */
    public DeleteDepartmentCommand(String key, String text, DepartmentController departmentController) {
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

        out.print("Enter the id of the department you with to modify: ");
        id = scanner.nextInt();
        scanner.nextLine();
        try {
            Department department = this.departmentController.delete(id);
            out.println("Deleted: " + department);
        } catch (NoSuchElementException ex) {
            out.println("Could not find the department with id " + id);
        }
    }
}
