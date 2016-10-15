package view.decorator;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by marius on 10/14/16.
 */
public class IndentablePrintStream extends PrintStreamDecorator {
    private Integer     indent;

    public IndentablePrintStream(PrintStream printStream, Integer indent) {
        super(printStream);
        this.indent = indent;
    }

    @Override
    public void println(String x) {
        this.printStream.print(StringUtils.repeat(" ", this.indent));
        super.println(x);
    }
}
