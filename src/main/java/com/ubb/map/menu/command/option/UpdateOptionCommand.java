package com.ubb.map.menu.command.option;

import com.ubb.map.controller.OptionController;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.Option;
import com.ubb.map.exception.InvalidObjectException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/28/16.
 */
public class UpdateOptionCommand extends BaseOptionCommand {
    public UpdateOptionCommand(String key, String description, OptionController controller) {
        super(key, description, controller);
    }

    @Override
    public void execute(Scanner scanner, PrintStream out) {
        Integer id;
        String idCand;
        String idDep;

        out.print("Enter the id of the option you with to modify: ");
        id = scanner.nextInt();
        scanner.nextLine();
        out.print("Enter the new candidate id of the option or leave it blank: ");
        idCand = scanner.nextLine();
        out.print("Enter the new department id or leave id blank to not change it: ");
        idDep = scanner.nextLine();

        try {
            Option option = this.optionController.update(id, idCand, idDep);
            out.println("Updated " + option);
        } catch (InvalidObjectException e) {
            out.println(e.getMessage());
        }
    }
}
