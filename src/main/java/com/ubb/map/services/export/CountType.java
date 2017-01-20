package com.ubb.map.services.export;

/**
 * Created by marius on 1/19/2017.
 */
public enum CountType {
    ALL("All entries(ignore filters)"),
    CURRENT_PAGE("Current page"),
    All_MATCHING("All results");

    private String name;

    CountType(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
