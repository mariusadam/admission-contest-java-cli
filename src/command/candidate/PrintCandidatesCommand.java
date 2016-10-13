package command.candidate;

import controller.CandidateController;
import domain.Candidate;
import domain.Entity;
import util.GenericArray;
import util.helper.PrintTableHelper;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class PrintCandidatesCommand extends AbstractCandidateCommand {
    private PrintTableHelper tableHelper;

    /**
     * @param key                 The key which identifies the command
     * @param text                Short description for the command
     * @param candidateController The controller which handles candidates
     */
    public PrintCandidatesCommand(String key, String text, CandidateController candidateController, PrintTableHelper tableHelper) {
        super(key, text, candidateController);
        this.tableHelper = tableHelper;
    }

    /**
     * Executes the current command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        String[] columns = {"id", "name", "phone", "address"};
        GenericArray<Entity> candidates = this.candidateController.getAll();
        Object[][] data = new Object[candidates.getSize()][];

        for (int i = 0; i < candidates.getSize(); i++) {
            Candidate candidate = (Candidate) candidates.getAt(i);
            Object[] row = {
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getPhone(),
                    candidate.getAddress(),
            };
            data[i] = row;
        }

        this.tableHelper.printItems(columns, data);
    }
}
