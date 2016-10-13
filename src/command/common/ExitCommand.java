package command.common;

import command.AbstractCommand;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by mariusadam on 10/13/16.
 */
public class ExitCommand extends AbstractCommand {
    /**
     * @param key  The key which identifies the command
     * @param text Short description for the command
     */
    public ExitCommand(String key, String text) {
        super(key, text);
    }

    /**
     * Executes the current command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        out.println("Exiting now...");
        System.exit(0);
    }
}
