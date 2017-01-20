package com.ubb.map.services.export.pdf;

import com.itextpdf.layout.element.Cell;
import com.ubb.map.domain.Department;
import com.ubb.map.services.export.ColumnsConfigurator;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by marius on 1/20/2017.
 */
public class DepartmentPdfExporter extends BasePdfExporter<Department> {
    public DepartmentPdfExporter(List<Department> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected List<Cell> getTableHeader() {
        return Stream.of(
                new Cell().add("Id"),
                new Cell().add("Code"),
                new Cell().add("Name"),
                new Cell().add("Number of seats"),
                new Cell().add("Created at"),
                new Cell().add("Updated at")
        ).collect(Collectors.toList());
    }

    @Override
    protected List<Callback<Department, String>> getColumnsCallBacks() {
        return ColumnsConfigurator.getDepartmentColumnsCallBacks();
    }
}
