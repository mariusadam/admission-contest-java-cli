package com.ubb.map.services.filters.simple;

import com.j256.ormlite.stmt.Where;
import com.ubb.map.services.filters.ValueProvider;

import java.sql.SQLException;

/**
 * Created by marius on 16.12.2016.
 */
public class NotEqualsFilter extends SimpleFilter {
    public NotEqualsFilter(String propertyName, ValueProvider valueProvider) {
        super(propertyName, valueProvider, "Not equals");
    }

    @Override
    public Boolean apply(Where<?, ?> where) throws SQLException {
        Object obj = transformValue(valueProvider.provideValue());
        if (obj != null) {
            where.ne(getPropertyName(), obj);
            return true;
        }

        return false;
    }
}
