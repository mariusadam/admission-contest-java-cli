package menu;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by marius on 10/13/16.
 */
public class Menu extends MenuItem {
    private Map<String, MenuItemInterface> commandMap;

    public Menu(String key, String description) {
        super(key, description);
        this.commandMap = new TreeMap<>();
    }

    public void addItem(MenuItemInterface command) {
        if (command.getParent() != null) {
            throw new IllegalStateException("This item already has a defined parent.");
        }
        if (this.commandMap.get(this.getContextualKey(command.getKey())) != null) {
            throw new IllegalStateException(String.format("A naming collision occurred for (%s), please configure the menus first, and them the simple commands" +
                    " and do not use the same key on the same level!", command.getDescription()));
        }
        command.setParent(this);
        this.commandMap.put(this.getContextualKey(command.getKey()), command);
    }

    private void printItems(PrintStream out) {
        for(MenuItemInterface command : this.commandMap.values()) {
            out.println(command.getKey() + " - " + command.getDescription());
        }
    }

    @Override
    public void execute(Scanner scanner, PrintStream out) {
        while (true) {
            try {
                this.printItems(out);
                String cmdStr = scanner.nextLine();
                MenuItemInterface command = this.commandMap.get(this.getContextualKey(cmdStr));
                if (command == null) {
                    out.println("Unknown menu selection: " + cmdStr);
                    continue;
                }
                command.execute(scanner, out);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private String getContextualKeyRec(MenuItemInterface node) {
        if (node.getParent() == null) {
            return node.getKey();
        }
        return getContextualKeyRec(node.getParent()) + node.getKey();
    }

    private String getContextualKey(String key) {
        return this.getContextualKeyRec(this) + key;
    }
}
