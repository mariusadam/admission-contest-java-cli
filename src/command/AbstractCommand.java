package command;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public abstract class AbstractCommand {
    private String key;
    private String text;

    /**
     *
     * @param key                 The key which identifies the command
     * @param text                Short description for the command
     */
    public AbstractCommand(String key, String text) {
        this.key = key;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    /**
     * Executes the current command
     * @param scanner
     * @param out
     */
    abstract public void execute(Scanner scanner, PrintStream out);
}
