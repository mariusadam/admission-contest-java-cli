package view.menu;

import command.CommandInterface;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by marius on 10/14/16.
 */
public interface MenuInterface {
    public Scanner getScanner();
    public PrintStream getOut();
    public void show();
    public void addCommand(CommandInterface commandInterface);
}
