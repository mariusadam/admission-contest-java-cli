package menu.command.candidate;

import controller.CandidateController;
import domain.Candidate;
import exception.InvalidCandidateException;
import exception.InvalidEntityException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class UpdateCandidateCommand extends BaseCandidateCommand {

    /**
     * @param key                 The key which identifies the menu.command
     * @param text                Short description for the menu.command
     * @param candidateController The controller which handles candidates
     */
    public UpdateCandidateCommand(String key, String text, CandidateController candidateController) {
        super(key, text, candidateController);
    }

    /**
     * Executes the current menu.command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        Integer id;
        String name;
        String phone;
        String address;

        out.print("Enter the id of the candidate you wish to modify: ");
        id = scanner.nextInt();
        scanner.nextLine();
        out.print("Enter the new name of the candidate or leave id blank to not change it: ");
        name = scanner.nextLine();
        out.print("Enter the new phone of the candidate or leave id blank to not change it: ");
        phone = scanner.nextLine();
        out.print("Enter the new addressof the candidate or leave id blank to not change it: ");
        address = scanner.nextLine();

        try {
            Candidate cand = this.candidateController.update(id, name, phone, address);
            out.println("Updated " + cand);
        } catch (InvalidEntityException e) {
            e.printStackTrace(out);
        }
    }
}
