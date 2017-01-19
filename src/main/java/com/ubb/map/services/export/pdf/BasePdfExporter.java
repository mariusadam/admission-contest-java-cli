package com.ubb.map.services.export.pdf;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.ubb.map.services.export.BaseExporter;
import javafx.util.Callback;

import java.util.List;

/**
 * Created by marius on 1/19/2017.
 */
public abstract class BasePdfExporter<T> extends BaseExporter<T> {
    public BasePdfExporter(List<T> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected Object call() throws Exception {
        List<Callback<T, String>> columnsCallbacks = getColumnsCallBacks();

        int size = items.size();
        int columnsCount = columnsCallbacks.size();

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(destinationPath));
        Document doc = new Document(pdfDoc);
        Table table;
        table = new Table(columnsCount);
        setTableHeader(table);
        int sleepTime = 2000 / size;
        for (int i = 0; i < size; i++) {
            for (Callback<T, String> columnsCallback : columnsCallbacks) {
                String content = columnsCallback.call(items.get(i));
                table.addCell(content);
            }
            Thread.sleep(sleepTime);
            updateMessage("Added " + items.get(i));
            updateProgress(i, size - 1);
        }
        updateMessage("");
        updateMessage("Done exporting " + size + " objects to pdf");
        doc.add(table);
        doc.close();
        return true;
    }

    protected abstract void setTableHeader(Table table);

    protected abstract List<Callback<T, String>> getColumnsCallBacks();
}
