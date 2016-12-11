package com.ubb.map.helper.saver;

import com.ubb.map.domain.Entity;

/**
 * @author Marius Adam.
 */
public class CsvFileSaver<T> extends FileSaver<T> {
    private String separator;
    public final static String DEFAULT_SEPARATOR = " | ";

    public CsvFileSaver() {
        this(DEFAULT_SEPARATOR);
    }

    public CsvFileSaver(String separator) {
        this.separator = separator;
    }

    @Override
    protected String transform(Object object) {
        return ((Entity)object).toCsvFormat(this.separator);
    }
}
