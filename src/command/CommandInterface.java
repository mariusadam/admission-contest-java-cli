package command;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public interface CommandInterface {
    /**
     *
     * @return The key which indentifies the command
     */
    public String getKey();

    /**
     *
     * @return A short Description of the command
     */
    public String getDescription();

    /**
     * Executes the current command
     * @param scanner
     * @param out
     */
    public void execute(Scanner scanner, PrintStream out);
}
