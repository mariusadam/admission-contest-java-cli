package menu.command.candidate;

import controller.CandidateController;
import domain.Candidate;
import util.helper.PrintTableHelper;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class PrintCandidatesCommand extends BaseCandidateCommand {
    private PrintTableHelper tableHelper;

    /**
     * @param key                 The key which identifies the menu.command
     * @param text                Short description for the menu.command
     * @param candidateController The controller which handles candidates
     */
    public PrintCandidatesCommand(String key, String text, CandidateController candidateController, PrintTableHelper tableHelper) {
        super(key, text, candidateController);
        this.tableHelper = tableHelper;
    }

    /**
     * Executes the current menu.command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        String[] columns = {"id", "name", "phone", "address"};
        Collection<Candidate> candidates = this.candidateController.getAll();
        Object[][] data = new Object[candidates.size()][];

        int poz = 0;
        for (Candidate candidate : candidates) {
            Object[] row = {
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getPhone(),
                    candidate.getAddress(),
            };
            data[poz++] = row;
        }

        this.tableHelper.printItems(columns, data);
    }
}
