package com.ubb.map.services.export.pdf;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.ubb.map.domain.Candidate;
import com.ubb.map.services.export.BaseExporter;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marius on 1/19/2017.
 */
public class CandidatePdfExporter extends BasePdfExporter<Candidate> {
    public CandidatePdfExporter(List<Candidate> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected void setTableHeader(Table table) {
        table.addHeaderCell("Id");
        table.addHeaderCell("Name");
        table.addHeaderCell("Phone");
        table.addHeaderCell("Address");
        table.addHeaderCell("Created at");
        table.addHeaderCell("Updated at");
    }

    @Override
    protected List<Callback<Candidate, String>> getColumnsCallBacks() {
        List<Callback<Candidate, String>> result = new ArrayList<>();
        result.add(0, param -> param.getId().toString());
        result.add(1, Candidate::getName);
        result.add(2, Candidate::getPhone);
        result.add(3, Candidate::getAddress);
        result.add(4, param -> param.getCreatedAt().toString());
        result.add(5, param -> param.getUpdatedAt().toString());

        return result;
    }
}
