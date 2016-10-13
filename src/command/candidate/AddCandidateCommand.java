package command.candidate;

import controller.CandidateController;
import domain.Candidate;
import exception.CandidateException;

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
     */
    @Override
    public void execute() {
        String name;
        String phone;
        String address;

        this.out.print("Enter the name of the candidate: ");
        name = this.scanner.nextLine();
        this.out.print("Enter the phone of the candidate: ");
        phone = this.scanner.nextLine();
        this.out.print("Enter the address of the candidate: ");
        address = this.scanner.nextLine();

        try {
            Candidate cand = this.candidateController.create(name, phone, address);
            this.out.println("Added " + cand);
        } catch (CandidateException ex) {
            this.out.println(ex.getMessage());
        }
    }
}
