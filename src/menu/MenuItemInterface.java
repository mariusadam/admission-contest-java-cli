package menu;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public interface MenuItemInterface {
    /**
     *
     * @return The key which indentifies the menu.command
     */
    public String getKey();

    /**
     *
     * @return A short Description of the menu.command
     */
    public String getDescription();

    public MenuItemInterface getParent();

    public void setParent(MenuItemInterface parent);

    /**
     * Executes the current menu.command
     * @param scanner
     * @param out
     */
    public void execute(Scanner scanner, PrintStream out);
}
