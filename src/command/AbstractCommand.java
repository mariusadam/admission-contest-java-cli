package command;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public abstract class AbstractCommand implements CommandInterface {
    private String key;
    private String description;

    /**
     *
     * @param key                 The key which identifies the command
     * @param description                Short description for the command
     */
    public AbstractCommand(String key, String description) {
        this.key = key;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    public String getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Executes the current command
     * @param scanner
     * @param out
     */
    abstract public void execute(Scanner scanner, PrintStream out);
}
