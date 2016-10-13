package command.department;

import controller.DepartmentController;
import domain.Department;

import java.util.NoSuchElementException;

/**
 * Created by marius on 10/13/16.
 */
public class DeleteDepartmentCommand extends AbstractDepartmentCommand{
    /**
     * @param key                  The key which identifies the command
     * @param text                 Short description for the command
     * @param departmentController The controller which handles departments
     */
    public DeleteDepartmentCommand(String key, String text, DepartmentController departmentController) {
        super(key, text, departmentController);
    }

    /**
     * Executes the current command
     */
    @Override
    public void execute() {
        Integer id;

        this.out.print("Enter the id of the department you with to modify: ");
        id = this.scanner.nextInt();
        this.scanner.nextLine();
        try {
            Department department = this.departmentController.delete(id);
            this.out.println("Deleted: " + department);
        } catch (NoSuchElementException ex) {
            this.out.println("Could not find the department with id " + id);
        }
    }
}
