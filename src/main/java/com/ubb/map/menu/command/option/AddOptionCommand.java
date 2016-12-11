package com.ubb.map.menu.command.option;

import com.ubb.map.services.OptionCrudService;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/16/16.
 */
public class AddOptionCommand extends BaseOptionCommand {
    public AddOptionCommand(String key, String description, OptionCrudService controller) {
        super(key, description, controller);
    }

    @Override
    public void execute(Scanner scanner, PrintStream out) {
        String candidateId, departmentId;

        out.print("Enter the id of the candidate: ");
        candidateId = scanner.nextLine();

        out.print("Enter the id of the department: ");
        departmentId = scanner.nextLine();

        try {
            this.optionCrudService.create(candidateId, departmentId);
        } catch (InvalidObjectException | DuplicateEntryException e) {
            out.println(e.getMessage());
        }
    }
}
