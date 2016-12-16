package com.ubb.map.services.filters.types.multiple;

import com.ubb.map.services.filters.types.NamedFilter;
import com.ubb.map.services.filters.types.PropertyFilter;
import com.ubb.map.services.filters.types.simple.ValueProvider;

/**
 * Created by marius on 16.12.2016.
 */
public abstract class MultipleFilter extends NamedFilter implements PropertyFilter {
    protected ValueProvider firstProvider;
    protected ValueProvider secondProvider;

    public MultipleFilter(String propertyName, ValueProvider firstProvider, ValueProvider secondProvider, String filterName) {
        super(propertyName, filterName);
        this.firstProvider = firstProvider;
        this.secondProvider = secondProvider;
    }

    @Override
    public boolean isSingleValued() {
        return false;
    }
}
