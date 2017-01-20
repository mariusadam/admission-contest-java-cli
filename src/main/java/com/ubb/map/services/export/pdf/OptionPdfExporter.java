package com.ubb.map.services.export.pdf;

import com.itextpdf.layout.element.Cell;
import com.ubb.map.domain.Option;
import com.ubb.map.services.export.ColumnsConfigurator;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by marius on 1/20/2017.
 */
public class OptionPdfExporter extends BasePdfExporter<Option> {
    public OptionPdfExporter(List<Option> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected List<Cell> getTableHeader() {
        return Stream.of(
                new Cell().add("Id"),
                new Cell().add("Candidate id"),
                new Cell().add("Candidate name"),
                new Cell().add("Department id"),
                new Cell().add("Department name"),
                new Cell().add("Created at"),
                new Cell().add("Updated at")
        ).collect(Collectors.toList());
    }

    @Override
    protected List<Callback<Option, String>> getColumnsCallBacks() {
        return ColumnsConfigurator.getOptionColumnsCallBacks();
    }
}
