package com.ubb.map.services.export.csv;

import com.ubb.map.domain.Candidate;
import com.ubb.map.services.export.ColumnsConfigurator;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by marius on 1/19/2017.
 */
public class CandidateCsvExporter extends BaseCsvExporter<Candidate> {
    public CandidateCsvExporter(List<Candidate> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected List<String> getColumnsNames() {
        return Stream.of(
                "Id",
                "Name",
                "Phone",
                "Address",
                "Created at",
                "Updated at"
        ).collect(Collectors.toList());
    }

    @Override
    protected List<Callback<Candidate, String>> getColumnsCallBacks() {
        return ColumnsConfigurator.getCandidateColumnsCallBacks();
    }
}
