package view.menu.decorator;

import org.apache.commons.lang.StringUtils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by marius on 10/14/16.
 */
public class PrintStreamDecorator extends PrintStream {
    private PrintStream decorated;
    private Integer     indent;

    /**
     * Creates a new print stream.  This stream will not flush automatically.
     *
     * @param out The output stream to which values and objects will be
     *            printed
     * @see PrintWriter#PrintWriter(OutputStream)
     */
    public PrintStreamDecorator(PrintStream out, Integer indent) {
        super(out);
        this.decorated         = out;
        this.indent            = indent;
    }

    /**
     * Prints a String and then terminate the line.  This method behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x The <code>String</code> to be printed.
     */
    @Override
    public void println(String x) {
        decorated.print(StringUtils.repeat(" ", this.indent));
        super.println(x);
    }
}
