package command.department;

import controller.DepartmentController;
import domain.Department;
import exception.DepartmentException;

/**
 * Created by marius on 10/13/16.
 */
public class AddDepartmentCommand extends AbstractDepartmentCommand {

    /**
     * @param key                  The key which identifies the command
     * @param text                 Short description for the command
     * @param departmentController The controller which handles departments
     */
    public AddDepartmentCommand(String key, String text, DepartmentController departmentController) {
        super(key, text, departmentController);
    }

    /**
     * Executes the current command
     */
    @Override
    public void execute() {
        String name;
        Integer numberOfSeats;

        this.out.print("Enter the nae of the department: ");
        name = this.scanner.nextLine();
        this.out.print("Enter the number of seats of the department: ");
        numberOfSeats = this.scanner.nextInt();
        this.scanner.nextLine();

        try {
            Department department = this.departmentController.create(name, numberOfSeats);
            this.out.println("Updated " + department);
        } catch (DepartmentException ex) {
            this.out.println(ex.getMessage());
        }
    }
}
