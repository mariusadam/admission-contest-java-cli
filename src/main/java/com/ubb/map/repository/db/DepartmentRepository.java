package com.ubb.map.repository.db;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Department;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.repository.qualifiers.Connection;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marius on 11.12.2016.
 */
public class DepartmentRepository extends OrmRepository<Integer, Department> {
    @Inject
    public DepartmentRepository(@Connection ConnectionSource connection) {
        super(connection, Department.class);
    }

    public Department findByCode(String code) throws SQLException, RepositoryException {
        List<Department> result = dao.queryForEq("code", code);
        if (result.isEmpty()) {
            return null;
        }

        if (result.size() > 1) {
            throw new RepositoryException("The supplied code matches to more than one department");
        }

        return result.get(0);
    }

    public Collection<String> suggestCodes(String partialCode) throws SQLException {
        QueryBuilder<Department, Integer> qb = dao.queryBuilder();
        String[] colums = {"code"};
        qb
                .limit((long) 10)
                .selectColumns(colums)
                .where().
                like("code", "%" + partialCode + "%");
        List<Department> departments = qb.query();
        return departments
                .stream()
                .map(Department::getCode)
                .collect(Collectors.toList());
    }
}
