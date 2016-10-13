package command.candidate;

import command.AbstractCommand;
import controller.CandidateController;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
abstract public class AbstractCandidateCommand extends AbstractCommand {
    protected CandidateController candidateController;

    /**
     *
     * @param key                 The key which identifies the command
     * @param text                Short description for the command
     * @param candidateController The controller wich handles candidates
     */
    public AbstractCandidateCommand(String key, String text, CandidateController candidateController) {
        super(key, text);
        this.candidateController = candidateController;
    }

    /**
     * Executes the current command
     * @param scanner
     * @param out
     */
    abstract public void execute(Scanner scanner, PrintStream out);
}
