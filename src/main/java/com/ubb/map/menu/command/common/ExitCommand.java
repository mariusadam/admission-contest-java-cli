package com.ubb.map.menu.command.common;

import com.ubb.map.menu.MenuItem;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by mariusadam on 10/13/16.
 */
public class ExitCommand extends MenuItem {
    /**
     * @param key  The key which identifies the com.ubb.map.menu.command
     * @param text Short description for the com.ubb.map.menu.command
     */
    public ExitCommand(String key, String text) {
        super(key, text);
    }

    /**
     * Executes the current com.ubb.map.menu.command
     * @param scanner
     * @param out
     */
    @Override
    public void execute(Scanner scanner, PrintStream out) {
        out.println("Exiting now...");
        System.exit(0);
    }
}
