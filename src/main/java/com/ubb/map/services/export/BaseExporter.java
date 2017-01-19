package com.ubb.map.services.export;

import javafx.concurrent.Task;

import java.util.List;

/**
 * Created by marius on 1/19/2017.
 */
public abstract class BaseExporter<T> extends Task {
    public static final int MAX_STEP = 100;
    public static final int MAX_SLEEP_TIME = 10000; //4sec
    protected List<T> items;
    protected String destinationPath;

    public BaseExporter(List<T> items, String destinationPath) {
        this.items = items;
        this.destinationPath = destinationPath;
    }

    protected double getPercentage(int currentStep, int total) {
        return (MAX_STEP * currentStep * 1.0) / total;
    }

    protected int getSleepTimePerStep(int total) {
        return (int)((MAX_SLEEP_TIME / 10.0) / total) * 10;
    }
}
