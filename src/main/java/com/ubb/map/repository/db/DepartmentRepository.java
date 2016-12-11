package com.ubb.map.repository.db;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.HasId;

/**
 * Created by marius on 11.12.2016.
 */
public class DepartmentRepository extends OrmRepository<Integer, Department> {
    public DepartmentRepository(ConnectionSource connection, Class<Department> departmentClass) {
        super(connection, departmentClass);
    }
}
