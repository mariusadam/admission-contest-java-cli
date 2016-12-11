package com.ubb.map.repository.db;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.HasId;
import com.ubb.map.domain.Option;

/**
 * Created by marius on 11.12.2016.
 */
public class OptionRepository extends OrmRepository<Integer, Option> {
    public OptionRepository(ConnectionSource connection, Class<Option> optionClass) {
        super(connection, optionClass);
    }
}
