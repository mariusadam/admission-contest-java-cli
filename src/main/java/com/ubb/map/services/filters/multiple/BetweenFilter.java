package com.ubb.map.services.filters.multiple;

import com.j256.ormlite.stmt.Where;
import com.ubb.map.services.filters.ValueProvider;

import java.sql.SQLException;

/**
 * Created by marius on 16.12.2016.
 */
public class BetweenFilter extends MultipleFilter {
    public BetweenFilter(String propertyName, ValueProvider firstProvider, ValueProvider secondProvider) {
        super(propertyName, firstProvider, secondProvider, "Between");
    }

    @Override
    public Boolean apply(Where<?, ?> where) throws SQLException {
        Object first = transformValue(firstProvider.provideValue());
        Object second = transformValue(secondProvider.provideValue());

        if (first != null || second != null) {
            if (first == null) {
                where.le(getPropertyName(), second);
            } else if (second == null) {
                where.ge(getPropertyName(), first);
            } else {
                where.between(getPropertyName(), first, second);
            }

            return true;
        }

        return false;
    }
}
