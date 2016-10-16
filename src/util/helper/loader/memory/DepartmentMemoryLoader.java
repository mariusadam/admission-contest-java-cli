package util.helper.loader.memory;

import domain.Department;
import domain.Entity;
import exception.DuplicateEntryException;
import org.apache.commons.lang.RandomStringUtils;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/15/16.
 */
public class DepartmentMemoryLoader<T extends Department> extends BaseMemoryLoader<T>{
    @Override
    public Entity getNewEntity(RepositoryInterface<T> repository) {
        return new Department(
                repository.getNextId(),
                "Department" + repository.getNextId(),
                Integer.valueOf(RandomStringUtils.randomNumeric(3))
        );
    }
}
