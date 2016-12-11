package com.ubb.map.menu.command.candidate;

import com.ubb.map.services.CandidateCrudService;
import com.ubb.map.domain.Candidate;
import com.ubb.map.helper.PrintTableHelper;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class PrintCandidatesCommand extends BaseCandidateCommand {
    private PrintTableHelper tableHelper;

    /**
     * @param key                 The key which identifies the com.ubb.map.menu.command
     * @param text                Short description for the com.ubb.map.menu.command
     * @param candidateCrudService The com.ubb.map.services which handles candidates
     */
    public PrintCandidatesCommand(String key, String text, CandidateCrudService candidateCrudService, PrintTableHelper tableHelper) {
        super(key, text, candidateCrudService);
        this.tableHelper = tableHelper;
    }

    /**
     * Executes the current com.ubb.map.menu.command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        String[] columns = {"id", "name", "phone", "address"};
        Collection<Candidate> candidates = this.candidateCrudService.getAll();
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
