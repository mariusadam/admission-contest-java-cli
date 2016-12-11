package com.ubb.map.helper.loader.memory;

import com.ubb.map.domain.Department;
import com.ubb.map.helper.generator.RandomGenerator;
import org.apache.commons.lang.RandomStringUtils;

/**
 * @author Marius Adam
 */
public class DepartmentMemoryLoader extends BaseMemoryLoader<Department>{
    @Override
    public Department getNewEntity() {
        int id = RandomGenerator.getRandomInt();
        return new Department(
                id,
                "Department" + id,
                Integer.valueOf(RandomStringUtils.randomNumeric(3))
        );
    }
}
