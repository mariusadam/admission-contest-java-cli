package com.ubb.map.services.filters;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

/**
 * Created by marius on 16.12.2016.
 */
public interface PropertyFilter {
    /**
     * @param where
     * @return true if some filters where applied, false otherwise
     * @throws SQLException
     */
    Boolean apply(Where<?, ?> where) throws SQLException;
}
