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
     * @param scanner             The input scanner
     * @param out                 The output stream
     * @param candidateController The controller which handles candidates
     */
    public AbstractCandidateCommand(String key, String text, Scanner scanner, PrintStream out, CandidateController candidateController) {
        super(key, text, scanner, out);
        this.candidateController = candidateController;
    }

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
     */
    abstract public void execute();
}
