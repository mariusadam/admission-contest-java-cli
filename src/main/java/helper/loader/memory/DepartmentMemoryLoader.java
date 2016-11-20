package helper.loader.memory;

import domain.Department;
import helper.generator.RandomGenerator;
import org.apache.commons.lang.RandomStringUtils;

/**
 * @author Marius Adam
 */
public class DepartmentMemoryLoader extends BaseMemoryLoader<Department>{
    @Override
    public Department getNewEntity() {
        String id = RandomGenerator.getRandomString(15);
        return new Department(
                id,
                "Department" + id,
                Integer.valueOf(RandomStringUtils.randomNumeric(3))
        );
    }
}
