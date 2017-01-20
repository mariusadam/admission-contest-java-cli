package com.ubb.map.services.export.csv;

import com.ubb.map.domain.Option;
import com.ubb.map.services.export.ColumnsConfigurator;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by marius on 1/20/2017.
 */
public class OptionCsvExporter extends BaseCsvExporter<Option> {
    public OptionCsvExporter(List<Option> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected List<String> getColumnsNames() {
        return Stream.of(
                "id",
                "candidate_id",
                "candidate_name",
                "department_id",
                "department_name",
                "created_at",
                "updated_at"
        ).collect(Collectors.toList());
    }

    @Override
    protected List<Callback<Option, String>> getColumnsCallBacks() {
        return ColumnsConfigurator.getOptionColumnsCallBacks();
    }
}
