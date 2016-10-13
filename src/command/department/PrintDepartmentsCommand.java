package command.department;

import controller.DepartmentController;
import domain.Department;
import domain.Entity;
import util.GenericArray;
import util.helper.PrintTableHelper;

/**
 * Created by marius on 10/13/16.
 */
public class PrintDepartmentsCommand extends AbstractDepartmentCommand {
    private PrintTableHelper tableHelper;

    /**
     * @param key                  The key which identifies the command
     * @param text                 Short description for the command
     * @param departmentController The controller which handles departments
     */
    public PrintDepartmentsCommand(String key, String text, DepartmentController departmentController, PrintTableHelper tableHelper) {
        super(key, text, departmentController);
        this.tableHelper = tableHelper;
    }

    /**
     * Executes the current command
     */
    @Override
    public void execute() {
        String[] columns = {"id", "name", "number of seats"};
        GenericArray<Entity> departments = this.departmentController.getAll();
        Object[][] data = new Object[departments.getSize()][];

        for (int i = 0; i < departments.getSize(); i++) {
            Department department = (Department) departments.getAt(i);
            Object[] row = {
                    department.getId(),
                    department.getName(),
                    department.getNumberOfSeats(),
            };
            data[i] = row;
        }

        tableHelper.printItems(columns, data);
    }
}
