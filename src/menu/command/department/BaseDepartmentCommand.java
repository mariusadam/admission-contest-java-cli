package menu.command.department;

import menu.MenuItem;
import controller.DepartmentController;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
abstract class BaseDepartmentCommand extends MenuItem {
    DepartmentController departmentController;

    /**
     *
     * @param key                  The key which identifies the menu.command
     * @param text                 Short description for the menu.command
     * @param departmentController The controller which handles departments
     */
    BaseDepartmentCommand(String key, String text, DepartmentController departmentController) {
        super(key, text);
        this.departmentController = departmentController;
    }

    /**
     * Executes the current menu.command
     * @param scanner
     * @param out
     */
    abstract public void execute(Scanner scanner, PrintStream out);
}
