package com.ubb.map.services.filters;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

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

    protected Object transformValue(Object value) {
        if (value != null) {
            if (value instanceof LocalDate) {
                value = Date.from(((LocalDate) value).atStartOfDay(ZoneId.systemDefault()).toInstant());
            } else if (Objects.equals(value.toString(), "")) {
                value = null;
            }
        }

        return value;
    }
}
