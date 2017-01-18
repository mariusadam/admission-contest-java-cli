package com.ubb.map.services.filters;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

/**
 * Created by marius on 15.01.2017.
 */
public class NullFilter implements PropertyFilter {
    @Override
    public Boolean apply(Where<?, ?> where) throws SQLException {
        return false;
    }

    @Override
    public String toString() {
        return "";
    }
}
