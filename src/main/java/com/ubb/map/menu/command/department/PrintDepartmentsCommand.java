package com.ubb.map.menu.command.department;

import com.ubb.map.controller.DepartmentController;
import com.ubb.map.domain.Department;
import com.ubb.map.helper.PrintTableHelper;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class PrintDepartmentsCommand extends BaseDepartmentCommand {
    private PrintTableHelper tableHelper;

    /**
     * @param key                  The key which identifies the com.ubb.map.menu.command
     * @param text                 Short description for the com.ubb.map.menu.command
     * @param departmentController The com.ubb.map.controller which handles departments
     */
    public PrintDepartmentsCommand(String key, String text, DepartmentController departmentController, PrintTableHelper tableHelper) {
        super(key, text, departmentController);
        this.tableHelper = tableHelper;
    }

    /**
     * Executes the current com.ubb.map.menu.command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        String[] columns = {"id", "name", "number of seats"};
        Collection<Department> departments = this.departmentController.getAll();
        Object[][] data = new Object[departments.size()][];

        int poz = 0;
        for (Department dep : departments) {
            Object[] row = {
                    dep.getId(),
                    dep.getName(),
                    dep.getNumberOfSeats(),
            };
            data[poz++] = row;
        }

        tableHelper.printItems(columns, data);
    }
}