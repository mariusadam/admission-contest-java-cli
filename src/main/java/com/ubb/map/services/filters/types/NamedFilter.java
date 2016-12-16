package com.ubb.map.services.filters.types;

/**
 * Created by marius on 16.12.2016.
 */
public abstract class NamedFilter {
    private String filterName;
    private String propertyName;

    public NamedFilter(String propertyName, String filterName) {
        this.filterName = filterName;
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String toString() {
        return this.filterName;
    }
}
