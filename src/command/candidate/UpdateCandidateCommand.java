package command.candidate;

import command.AbstractCommand;
import controller.CandidateController;
import domain.Candidate;
import exception.CandidateException;

/**
 * Created by marius on 10/13/16.
 */
public class UpdateCandidateCommand extends AbstractCandidateCommand {

    /**
     * @param key                 The key which identifies the command
     * @param text                Short description for the command
     * @param candidateController The controller which handles candidates
     */
    public UpdateCandidateCommand(String key, String text, CandidateController candidateController) {
        super(key, text, candidateController);
    }

    /**
     * Executes the current command
     */
    @Override
    public void execute() {
        Integer id;
        String name;
        String phone;
        String address;

        this.out.print("Enter the id of the candidate you wish to modify: ");
        id = this.scanner.nextInt();
        this.scanner.nextLine();
        this.out.print("Enter the new name of the candidate or leave id blank to not change it: ");
        name = this.scanner.nextLine();
        this.out.print("Enter the new phone of the candidate or leave id blank to not change it: ");
        phone = this.scanner.nextLine();
        this.out.print("Enter the new addressof the candidate or leave id blank to not change it: ");
        address = this.scanner.nextLine();

        try {
            Candidate cand = this.candidateController.update(id, name, phone, address);
            this.out.println("Updated " + cand);
        } catch (CandidateException ex) {
            this.out.println(ex.getMessage());
        }
    }
}
