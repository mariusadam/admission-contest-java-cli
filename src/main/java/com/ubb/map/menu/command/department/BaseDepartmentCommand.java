package com.ubb.map.menu.command.department;

import com.ubb.map.menu.MenuItem;
import com.ubb.map.services.DepartmentCrudService;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
abstract class BaseDepartmentCommand extends MenuItem {
    DepartmentCrudService departmentCrudService;

    /**
     *
     * @param key                  The key which identifies the com.ubb.map.menu.command
     * @param text                 Short description for the com.ubb.map.menu.command
     * @param departmentCrudService The com.ubb.map.services which handles departments
     */
    BaseDepartmentCommand(String key, String text, DepartmentCrudService departmentCrudService) {
        super(key, text);
        this.departmentCrudService = departmentCrudService;
    }

    /**
     * Executes the current com.ubb.map.menu.command
     * @param scanner
     * @param out
     */
    abstract public void execute(Scanner scanner, PrintStream out);
}
