package com.ubb.map.menu.command.candidate;

import com.ubb.map.services.CandidateCrudService;
import com.ubb.map.domain.Candidate;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class DeleteCandidateCommand extends BaseCandidateCommand {

    /**
     * @param key                 The key which identifies the com.ubb.map.menu.command
     * @param text                Short description for the com.ubb.map.menu.command
     * @param candidateCrudService The com.ubb.map.services which handles candidates
     */
    public DeleteCandidateCommand(String key, String text, CandidateCrudService candidateCrudService) {
        super(key, text, candidateCrudService);
    }

    /**
     * Executes the current com.ubb.map.menu.command
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
            Candidate candidate = this.candidateCrudService.delete(id);
            out.println("Deleted: " + candidate);
        } catch (NoSuchElementException ex) {
            out.println("Could not find the candidate with id " + id);
        }
    }
}
