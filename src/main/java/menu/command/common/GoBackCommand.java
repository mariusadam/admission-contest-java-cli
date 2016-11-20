package menu.command.common;

import menu.MenuItem;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/16/16.
 */
public class GoBackCommand extends MenuItem {
    public GoBackCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute(Scanner scanner, PrintStream out) {
        if (this.getParent().getParent() != null) {
            this.getParent().getParent().execute(scanner, out);
        } else {
            out.println("Exiting now...");
            System.exit(0);
        }
    }
}
