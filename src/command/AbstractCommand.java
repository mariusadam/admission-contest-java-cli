package command;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public abstract class AbstractCommand {
    private String key;
    private String text;
    protected Scanner scanner;
    protected PrintStream out;

    /**
     *
     * @param key                 The key which identifies the command
     * @param text                Short description for the command
     * @param scanner             The input scanner
     * @param out                 The output stream
     */
    public AbstractCommand(String key, String text, Scanner scanner, PrintStream out) {
        this.key = key;
        this.text = text;
        this.scanner = scanner;
        this.out = out;
    }

    /**
     *
     * @param key                 The key which identifies the command
     * @param text                Short description for the command
     */
    public AbstractCommand(String key, String text) {
        this.key = key;
        this.text = text;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    /**
     * Executes the current command
     */
    abstract public void execute();
}
