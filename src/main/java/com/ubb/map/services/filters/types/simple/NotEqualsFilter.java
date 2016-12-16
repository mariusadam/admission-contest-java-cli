package com.ubb.map.services.filters.types.simple;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

/**
 * Created by marius on 16.12.2016.
 */
public class NotEqualsFilter extends SimpleFilter {
    public NotEqualsFilter(String propertyName, ValueProvider valueProvider) {
        super(propertyName, valueProvider, "Not equals");
    }

    @Override
    public void apply(Where<?, ?> where) throws SQLException {
        if (valueProvider.provideValue() != null) {
            where.ne(getPropertyName(), valueProvider.provideValue());
        }
    }
}
