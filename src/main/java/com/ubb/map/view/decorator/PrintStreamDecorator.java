package com.ubb.map.view.decorator;

import java.io.*;

/**
 * Created by marius on 10/15/16.
 */
public abstract class PrintStreamDecorator extends PrintStream implements Appendable, Cloneable, Flushable {
    protected final PrintStream printStream;

    public PrintStreamDecorator(PrintStream printStream) {
        super(printStream);
        this.printStream = printStream;
    }
}
