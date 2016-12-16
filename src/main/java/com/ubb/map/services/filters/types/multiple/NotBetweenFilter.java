package com.ubb.map.services.filters.types.multiple;

import com.j256.ormlite.stmt.Where;
import com.ubb.map.services.filters.types.simple.ValueProvider;

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
        Object first = firstProvider.provideValue();
        Object second = secondProvider.provideValue();

        if(first == null && second == null) {
            return;
        } else if (first == null) {
            where.gt(getPropertyName(), second);
        } else if (second == null) {
            where.lt(getPropertyName(), first);
        }

        where.not().between(getPropertyName(), first, second);
   }
}
