package command.candidate;

import controller.CandidateController;
import dnl.utils.text.table.TextTable;
import domain.Candidate;
import domain.Entity;
import util.GenericArray;
import util.helper.PrintTableHelper;

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
     */
    @Override
    public void execute() {
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
