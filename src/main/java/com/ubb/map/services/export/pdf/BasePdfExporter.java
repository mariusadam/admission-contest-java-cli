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
        getTableHeader().stream().map(cell -> {
            cell.setBorderBottom(new SolidBorder(Color.BLACK, 2));
            cell.setBorderLeft(new SolidBorder(Color.BLACK, 2));
            cell.setBorderTop(new SolidBorder(Color.BLACK, 2));
            return cell;
        }).forEach(table::addHeaderCell);

        int sleepTime = getSleepTimePerStep(size);
        for (int i = 0; i < size; i++) {
            for (Callback<T, String> columnsCallback : columnsCallbacks) {
                String content = columnsCallback.call(items.get(i));
                table.addCell(content);
            }
            if (i != size - 1) {
                Thread.sleep(sleepTime);
            }
            updateProgress(getPercentage(i, size), MAX_STEP);
            updateMessage("Added " + items.get(i));
        }
        doc.add(table);
        updateProgress(MAX_STEP, MAX_STEP);
        updateMessage("Done exporting " + size + " objects to pdf to file " + destinationPath);
        doc.close();
        return true;
    }

    protected abstract List<Cell> getTableHeader();

    protected abstract List<Callback<T, String>> getColumnsCallBacks();
}
