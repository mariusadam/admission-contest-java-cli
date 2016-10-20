package helper.loader.memory;

import domain.Department;
import domain.Entity;
import org.apache.commons.lang.RandomStringUtils;
import repository.RepositoryInterface;

/**
 * @author Marius Adam
 */
public class DepartmentMemoryLoader extends BaseMemoryLoader<Department>{
    @Override
    public Department getNewEntity(RepositoryInterface<Department> repository) {
        return new Department(
                repository.getNextId(),
                "Department" + repository.getNextId(),
                Integer.valueOf(RandomStringUtils.randomNumeric(3))
        );
    }
}
