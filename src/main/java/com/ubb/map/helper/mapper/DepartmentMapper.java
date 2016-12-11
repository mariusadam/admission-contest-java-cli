package com.ubb.map.helper.mapper;

import com.ubb.map.domain.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by marius on 11/20/16.
 */
public class DepartmentMapper implements MapperInterface<Integer,Department> {
    @Override
    public Department createObject(ResultSet row) throws SQLException {
        Department department = new Department();
        department.setId(row.getInt("id"));
        department.setName(row.getString("name"));
        department.setNumberOfSeats(row.getInt("number_of_seats"));

        return department;
    }

    @Override
    public Map<String, String> toMap(Department object) {
        Map<String, String> properties = new Hashtable<>();
        if (object.getId() != null) {
            properties.put("id", object.getId().toString());
        }
        properties.put("name", object.getName());
        properties.put("number_of_seats", object.getNumberOfSeats().toString());

        return properties;
    }
}
