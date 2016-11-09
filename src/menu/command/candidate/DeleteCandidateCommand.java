package menu.command.candidate;

import controller.CandidateController;
import domain.Candidate;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class DeleteCandidateCommand extends BaseCandidateCommand {

    /**
     * @param key                 The key which identifies the menu.command
     * @param text                Short description for the menu.command
     * @param candidateController The controller which handles candidates
     */
    public DeleteCandidateCommand(String key, String text, CandidateController candidateController) {
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
        out.print("Enter the id of the candidate you wish to delete: ");
        id = scanner.nextInt();
        scanner.nextLine();

        try {
            Candidate candidate = this.candidateController.delete(id);
            out.println("Deleted: " + candidate);
        } catch (NoSuchElementException ex) {
            out.println("Could not find the candidate with id " + id);
        }
    }
}
