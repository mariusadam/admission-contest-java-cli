package com.ubb.map.repository.db;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Option;
import com.ubb.map.repository.qualifiers.Connection;

import javax.inject.Inject;

/**
 * Created by marius on 11.12.2016.
 */
public class OptionRepository extends OrmRepository<Integer, Option> {

    @Inject
    public OptionRepository(@Connection ConnectionSource connection) {
        super(connection, Option.class);
    }
}
