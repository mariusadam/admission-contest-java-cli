package com.ubb.map.menu.command.option;

import com.ubb.map.controller.OptionController;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.Option;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by marius on 10/28/16.
 */
public class DeleteOptionCommand extends BaseOptionCommand {
    public DeleteOptionCommand(String key, String description, OptionController controller) {
        super(key, description, controller);
    }

    @Override
    public void execute(Scanner scanner, PrintStream out) {
        Integer id;

        out.print("Enter the id of the option you with to delete: ");
        id = scanner.nextInt();
        scanner.nextLine();
        try {
            Option option = this.optionController.delete(id);
            out.println("Deleted: " + option);
        } catch (NoSuchElementException ex) {
            out.println("Could not find the option with id " + id);
        }
    }
}
