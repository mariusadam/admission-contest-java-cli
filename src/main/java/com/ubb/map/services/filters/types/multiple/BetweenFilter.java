package com.ubb.map.services.filters.types.multiple;

import com.j256.ormlite.stmt.Where;
import com.ubb.map.services.filters.types.simple.ValueProvider;

import java.sql.SQLException;

/**
 * Created by marius on 16.12.2016.
 */
public class BetweenFilter extends MultipleFilter {
    public BetweenFilter(String propertyName, ValueProvider firstProvider, ValueProvider secondProvider) {
        super(propertyName, firstProvider, secondProvider, "Between");
    }

    @Override
    public void apply(Where<?, ?> where) throws SQLException {
        Object first = firstProvider.provideValue();
        Object second = secondProvider.provideValue();

        if(first == null && second == null) {
            return;
        } else if (first == null) {
            where.and().le(getPropertyName(), second);
        } else if (second == null) {
            where.and().ge(getPropertyName(), first);
        }

        where.and().between(getPropertyName(), first, second);
    }
}
