package menu.command.common;

import menu.MenuItem;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by mariusadam on 10/13/16.
 */
public class ExitCommand extends MenuItem {
    /**
     * @param key  The key which identifies the menu.command
     * @param text Short description for the menu.command
     */
    public ExitCommand(String key, String text) {
        super(key, text);
    }

    /**
     * Executes the current menu.command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        out.println("Exiting now...");
        System.exit(0);
    }
}
