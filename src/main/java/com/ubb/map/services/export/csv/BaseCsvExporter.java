package com.ubb.map.services.export.csv;

import com.ubb.map.services.export.BaseExporter;
import javafx.util.Callback;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

/**
 * Created by marius on 1/19/2017.
 */
public abstract class BaseCsvExporter<T> extends BaseExporter<T> {
    public BaseCsvExporter(List<T> items, String destinationPath) {
        super(items, destinationPath);
    }

    @Override
    protected Object call() throws Exception {
        int size = items.size();
        int sleepTime = getSleepTimePerStep(size);

        BufferedWriter writer = new BufferedWriter(new FileWriter(destinationPath));
        writer.write(getHeaderRow());
        writer.write(System.lineSeparator());
        for (int i = 0; i < size; i++) {
            writer.write(transform(items.get(i)));
            writer.write(System.lineSeparator());
            if (i != size - 1) {
                Thread.sleep(sleepTime);
            }
            updateMessage("Added " + items.get(i));
            updateProgress(getPercentage(i, size), MAX_STEP);
        }
        writer.close();
        updateProgress(MAX_STEP, MAX_STEP);
        updateMessage("Done exporting " + size + " objects to csv to file " + destinationPath);

        return true;
    }

    protected String transform(T obj) {
        StringBuilder sb = new StringBuilder();
        for (Callback<T, String> cb : getColumnsCallBacks()) {
            sb.append(cb.call(obj)).append(",");
        }

        return sb.toString().replaceAll(",+$", "");
    }

    private String getHeaderRow() {
        return String.join(",", getColumnsNames());
    }

    protected abstract List<String> getColumnsNames();

    protected abstract List<Callback<T, String>> getColumnsCallBacks();
}
