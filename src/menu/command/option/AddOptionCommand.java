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
        String candidateId, departmentId;

        out.print("Enter the id of the candidate: ");
        candidateId = scanner.nextLine();

        out.print("Enter the id of the department: ");
        departmentId = scanner.nextLine();

        try {
            this.optionController.create(candidateId, departmentId);
        } catch (InvalidObjectException | DuplicateEntryException e) {
            out.println(e.getMessage());
        }
    }
}
