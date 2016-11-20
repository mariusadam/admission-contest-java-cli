package menu.command.candidate;

import menu.MenuItem;
import controller.CandidateController;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
abstract class BaseCandidateCommand extends MenuItem {
    CandidateController candidateController;

    /**
     *
     * @param key                 The key which identifies the menu.command
     * @param text                Short description for the menu.command
     * @param candidateController The controller wich handles candidates
     */
    BaseCandidateCommand(String key, String text, CandidateController candidateController) {
        super(key, text);
        this.candidateController = candidateController;
    }
}
