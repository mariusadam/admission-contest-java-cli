package com.ubb.map.menu.command.candidate;

import com.ubb.map.services.CandidateCrudService;
import com.ubb.map.domain.Candidate;
import com.ubb.map.exception.InvalidObjectException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public class UpdateCandidateCommand extends BaseCandidateCommand {

    /**
     * @param key                 The key which identifies the com.ubb.map.menu.command
     * @param text                Short description for the com.ubb.map.menu.command
     * @param candidateCrudService The com.ubb.map.services which handles candidates
     */
    public UpdateCandidateCommand(String key, String text, CandidateCrudService candidateCrudService) {
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
        String name;
        String phone;
        String address;

        out.print("Enter the id of the candidate you wish to modify: ");
        id = scanner.nextInt();
        scanner.nextLine();
        out.print("Enter the new name of the candidate or leave id blank to not change it: ");
        name = scanner.nextLine();
        out.print("Enter the new phone of the candidate or leave id blank to not change it: ");
        phone = scanner.nextLine();
        out.print("Enter the new addressof the candidate or leave id blank to not change it: ");
        address = scanner.nextLine();

        try {
            Candidate cand = this.candidateCrudService.update(id, name, phone, address);
            out.println("Updated " + cand);
        } catch (InvalidObjectException e) {
            out.println(e.getMessage());
        }
    }
}
