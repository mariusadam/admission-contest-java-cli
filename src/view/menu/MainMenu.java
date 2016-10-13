package view.menu;

import command.AbstractCommand;
import command.CommandInterface;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by marius on 10/13/16.
 */
public class MainMenu {
    private Map<String, CommandInterface> commandMap;
    private Scanner                      scanner;
    private PrintStream                  out;

    public MainMenu(Scanner scanner, PrintStream out) {
        this.commandMap = new TreeMap<>();
        this.scanner    = scanner;
        this.out        = out;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public PrintStream getOut() {
        return out;
    }

    public void addCommand(CommandInterface command) {
        this.commandMap.put(command.getKey(), command);
    }

    private void printMenu() {
        for(CommandInterface command : this.commandMap.values()) {
            this.out.println(command.getKey() + " - " + command.getDescription());
        }
    }

    public void show() {
        while (true) {
            try {
                this.printMenu();
                String cmdStr = this.scanner.nextLine();
                CommandInterface command = this.commandMap.get(cmdStr);
                if (command == null) {
                    this.out.println("Unknown command: " + cmdStr);
                    continue;
                }
                command.execute(this.scanner, this.out);
            } catch (Exception ex) {
                this.out.println(ex.getMessage());
            }
        }
    }
}
