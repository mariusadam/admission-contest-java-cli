package com.ubb.map.services.export.pdf;

import com.itextpdf.layout.element.Cell;
import com.ubb.map.domain.Candidate;
import com.ubb.map.services.export.ColumnsConfigurator;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by marius on 1/19/2017.
 */
public class CandidatePdfExporter extends BasePdfExporter<Candidate> {
    public CandidatePdfExporter(List<Candidate> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected List<Cell> getTableHeader() {
        return Stream.of(
                new Cell().add("Id"),
                new Cell().add("Name"),
                new Cell().add("Phone"),
                new Cell().add("Address"),
                new Cell().add("Created at"),
                new Cell().add("Updated at")
        ).collect(Collectors.toList());
    }

    @Override
    protected List<Callback<Candidate, String>> getColumnsCallBacks() {
        return ColumnsConfigurator.getCandidateColumnsCallBacks();
    }
}
