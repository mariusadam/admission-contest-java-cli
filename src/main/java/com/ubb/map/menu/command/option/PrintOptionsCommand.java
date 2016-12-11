package com.ubb.map.menu.command.option;

import com.ubb.map.controller.OptionController;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.Option;
import com.ubb.map.helper.PrintTableHelper;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by marius on 10/28/16.
 */
public class PrintOptionsCommand extends BaseOptionCommand {
    private PrintTableHelper tableHelper;

    public PrintOptionsCommand(String key, String description, OptionController controller, PrintTableHelper helper) {
        super(key, description, controller);
        this.tableHelper = helper;
    }

    @Override
    public void execute(Scanner scanner, PrintStream out) {
        String[] columns = {"id", "candidate name", "department name"};
        Collection<Option> departments = this.optionController.getAll();
        Object[][] data = new Object[departments.size()][];

        int poz = 0;
        for (Option dep : departments) {
            Object[] row = {
                    dep.getId(),
                    dep.getCandidate().getName(),
                    dep.getDepartment().getName(),
            };
            data[poz++] = row;
        }

        tableHelper.printItems(columns, data);
    }
}
