package command.candidate;

import controller.CandidateController;
import domain.Candidate;
import exception.CandidateException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class AddCandidateCommand extends AbstractCandidateCommand {

    /**
     * @param key                 The key which identifies the command
     * @param text                Short description for the command
     * @param candidateController The controller which handles candidates
     */
    public AddCandidateCommand(String key, String text, CandidateController candidateController) {
        super(key, text, candidateController);
    }

    /**
     * Executes the current command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        String name;
        String phone;
        String address;

        out.print("Enter the name of the candidate: ");
        name = scanner.nextLine();
        out.print("Enter the phone of the candidate: ");
        phone = scanner.nextLine();
        out.print("Enter the address of the candidate: ");
        address = scanner.nextLine();

        try {
            Candidate cand = this.candidateController.create(name, phone, address);
            out.println("Added " + cand);
        } catch (CandidateException ex) {
            out.println(ex.getMessage());
        }
    }
}