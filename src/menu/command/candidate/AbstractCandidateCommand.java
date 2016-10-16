package menu.command.candidate;

import menu.MenuItem;
import controller.CandidateController;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
abstract public class AbstractCandidateCommand extends MenuItem {
    protected CandidateController candidateController;

    /**
     *
     * @param key                 The key which identifies the menu.command
     * @param text                Short description for the menu.command
     * @param candidateController The controller wich handles candidates
     */
    public AbstractCandidateCommand(String key, String text, CandidateController candidateController) {
        super(key, text);
        this.candidateController = candidateController;
    }

    /**
     * Executes the current menu.command
     * @param scanner
     * @param out
     */
    abstract public void execute(Scanner scanner, PrintStream out);
}
