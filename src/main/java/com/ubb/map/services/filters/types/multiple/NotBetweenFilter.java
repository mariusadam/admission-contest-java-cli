package com.ubb.map.services.filters.types.multiple;

import com.j256.ormlite.stmt.Where;
import com.ubb.map.services.filters.types.ValueProvider;

import java.sql.SQLException;

/**
 * Created by marius on 16.12.2016.
 */
public class NotBetweenFilter extends MultipleFilter {

    public NotBetweenFilter(String propertyName, ValueProvider firstProvider, ValueProvider secondProvider) {
        super(propertyName, firstProvider, secondProvider, "Not between");
    }

    @Override
    public void apply(Where<?, ?> where) throws SQLException {
        Object first = firstProvider.provideValue().toString();
        Object second = secondProvider.provideValue().toString();

        if (!first.equals("") || !second.equals("")) {
            if (first.equals("")) {
                where.gt(getPropertyName(), second);
            } else if (second.equals("")) {
                where.lt(getPropertyName(), first);
            } else {
                where.not().between(getPropertyName(), first, second);
            }
        }
   }
}
