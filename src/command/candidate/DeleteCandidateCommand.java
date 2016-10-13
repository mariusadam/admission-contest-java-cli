package command.candidate;

import controller.CandidateController;
import domain.Candidate;

import java.util.NoSuchElementException;

/**
 * Created by marius on 10/13/16.
 */
public class DeleteCandidateCommand extends AbstractCandidateCommand{

    /**
     * @param key                 The key which identifies the command
     * @param text                Short description for the command
     * @param candidateController The controller which handles candidates
     */
    public DeleteCandidateCommand(String key, String text, CandidateController candidateController) {
        super(key, text, candidateController);
    }

    /**
     * Executes the current command
     */
    @Override
    public void execute() {
        Integer id;
        this.out.print("Enter the id of the candidate you wish to delete: ");
        id = this.scanner.nextInt();
        this.scanner.nextLine();

        try {
            Candidate candidate = this.candidateController.delete(id);
            this.out.println("Deleted: " + candidate);
        } catch (NoSuchElementException ex) {
            this.out.println("Could not find the candidate with id " + id);
        }
    }
}
