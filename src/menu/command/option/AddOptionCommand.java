package menu.command.option;

import controller.OptionController;
import exception.DuplicateEntryException;
import exception.InvalidObjectException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/16/16.
 */
public class AddOptionCommand extends BaseOptionCommand {
    public AddOptionCommand(String key, String description, OptionController controller) {
        super(key, description, controller);
    }

    @Override
    public void execute(Scanner scanner, PrintStream out) {
        Integer candidateId, departmentId;

        out.print("Enter the id of the candidate: ");
        candidateId = scanner.nextInt();

        out.print("Enter the id of the department: ");
        departmentId = scanner.nextInt();

        try {
            this.optionController.create(candidateId, departmentId);
        } catch (InvalidObjectException | DuplicateEntryException e) {
            e.printStackTrace(out);
        }
    }
}