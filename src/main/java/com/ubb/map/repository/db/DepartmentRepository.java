package com.ubb.map.repository.db;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.HasId;
import com.ubb.map.repository.qualifiers.ConnectionSingleton;

import javax.inject.Inject;

/**
 * Created by marius on 11.12.2016.
 */
public class DepartmentRepository extends OrmRepository<Integer, Department> {
    @Inject
    public DepartmentRepository(@ConnectionSingleton ConnectionSource connection) {
        super(connection, Department.class);
    }
}
