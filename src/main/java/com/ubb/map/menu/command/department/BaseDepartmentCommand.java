package com.ubb.map.menu.command.department;

import com.ubb.map.menu.MenuItem;
import com.ubb.map.controller.DepartmentController;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
abstract class BaseDepartmentCommand extends MenuItem {
    DepartmentController departmentController;

    /**
     *
     * @param key                  The key which identifies the com.ubb.map.menu.command
     * @param text                 Short description for the com.ubb.map.menu.command
     * @param departmentController The com.ubb.map.controller which handles departments
     */
    BaseDepartmentCommand(String key, String text, DepartmentController departmentController) {
        super(key, text);
        this.departmentController = departmentController;
    }

    /**
     * Executes the current com.ubb.map.menu.command
     * @param scanner
     * @param out
     */
    abstract public void execute(Scanner scanner, PrintStream out);
}
