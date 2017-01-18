package com.ubb.map.services.filters.simple;

import com.ubb.map.services.filters.NamedFilter;
import com.ubb.map.services.filters.PropertyFilter;
import com.ubb.map.services.filters.ValueProvider;

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
