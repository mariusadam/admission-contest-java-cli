package com.ubb.map.services.export.csv;

import com.ubb.map.domain.Department;
import com.ubb.map.services.export.ColumnsConfigurator;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by marius on 1/20/2017.
 */
public class DepartmentCsvExporter extends BaseCsvExporter<Department> {
    public DepartmentCsvExporter(List<Department> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected List<String> getColumnsNames() {
        return Stream.of(
                "id",
                "code",
                "name",
                "number_of_seats",
                "created_at",
                "updated_at"
        ).collect(Collectors.toList());
    }

    @Override
    protected List<Callback<Department, String>> getColumnsCallBacks() {
        return ColumnsConfigurator.getDepartmentColumnsCallBacks();
    }
}
