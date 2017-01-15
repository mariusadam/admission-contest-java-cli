package com.ubb.map.services.filters.types.simple;

import com.ubb.map.services.filters.types.NamedFilter;
import com.ubb.map.services.filters.types.PropertyFilter;
import com.ubb.map.services.filters.types.ValueProvider;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by marius on 16.12.2016.
 */
public abstract class SimpleFilter extends NamedFilter implements PropertyFilter {
    protected ValueProvider valueProvider;

    public SimpleFilter(String propertyName, ValueProvider valueProvider, String filterName) {
        super(propertyName, filterName);
        this.valueProvider = valueProvider;
    }
}
