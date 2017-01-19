package com.ubb.map.services.export;

/**
 * Created by marius on 1/19/2017.
 */
public enum CountType {
    ALL("All results"),
    CURRENT_PAGE("Current page");

    private String name;

    CountType(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
