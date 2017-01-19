package com.ubb.map.services.export;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.Table;
import javafx.concurrent.Task;

import java.io.File;
import java.util.List;

/**
 * Created by marius on 1/19/2017.
 */
public abstract class BaseExporter<T> extends Task {
    protected List<T> items;
    protected String destinationPath;

    public BaseExporter(List<T> items, String destinationPath) {
        this.items = items;
        this.destinationPath = destinationPath;
    }

}
