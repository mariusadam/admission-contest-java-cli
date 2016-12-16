package com.ubb.map.services.filters.types;

import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;

/**
 * Created by marius on 16.12.2016.
 */
public interface PropertyFilter {
    void apply(Where<?, ?> where) throws SQLException;
    boolean isSingleValued();
}
