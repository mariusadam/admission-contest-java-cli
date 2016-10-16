package menu;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/13/16.
 */
public abstract class MenuItem implements MenuItemInterface {
    private String key;
    private String description;
    private MenuItemInterface parent;

    /**
     *
     * @param key                 The key which identifies the menu.command
     * @param description                Short description for the menu.command
     */
    public MenuItem(String key, String description) {
        this.key = key;
        this.description = description;
        this.parent = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public MenuItemInterface getParent() {
        return this.parent;
    }

    @Override
    public void setParent(MenuItemInterface parent) {
        this.parent = parent;
    }

    /**
     * Executes the current menu.command
     * @param scanner
     * @param out
     */
    abstract public void execute(Scanner scanner, PrintStream out);
}
