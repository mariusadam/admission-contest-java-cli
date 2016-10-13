package util.helper;

import dnl.utils.text.table.TextTable;

import java.io.PrintStream;

/**
 * Created by marius on 10/13/16.
 */
public class PrintTableHelper {
    private Integer     indent;
    private PrintStream out;

    public PrintTableHelper(Integer indent, PrintStream out) {
        this.indent = indent;
        this.out = out;
    }

    public void printItems(String[] columns, Object[][] data) {
        TextTable table = new TextTable(columns, data);
        table.printTable(this.out, this.indent);
        this.out.println();
    }
}
