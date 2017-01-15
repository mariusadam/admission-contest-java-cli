package com.ubb.map.services.filters.types.simple;

import com.j256.ormlite.stmt.Where;
import com.ubb.map.services.filters.types.ValueProvider;

import java.sql.SQLException;

/**
 * Created by marius on 16.12.2016.
 */
public class NotContainsFilter extends SimpleFilter {
    public NotContainsFilter(String propertyName, ValueProvider valueProvider) {
        super(propertyName, valueProvider, "Not contains");
    }

    @Override
    public Boolean apply(Where<?, ?> where) throws SQLException {
        Object obj = transformValue(valueProvider.provideValue());
        if (obj != null) {
            where.not().like(getPropertyName(), String.format("%%%s%%", obj));
            return true;
        }

        return false;
    }
}
