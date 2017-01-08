package com.ubb.map.services.filters.types.simple;

import com.j256.ormlite.stmt.Where;
import com.ubb.map.services.filters.types.ValueProvider;

import java.sql.SQLException;

/**
 * Created by marius on 16.12.2016.
 */
public class ContainsFilter extends SimpleFilter {
    public ContainsFilter(String propertyName, ValueProvider valueProvider) {
        super(propertyName, valueProvider, "Contains");
    }

    @Override
    public void apply(Where<?, ?> where) throws SQLException {
        if (valueProvider.provideValue() != null) {
            where.like(getPropertyName(), String.format("%%%s%%", valueProvider.provideValue()));
        }
    }
}
