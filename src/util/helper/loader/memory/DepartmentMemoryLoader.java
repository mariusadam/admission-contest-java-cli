package util.helper.loader.memory;

import domain.Department;
import org.apache.commons.lang.RandomStringUtils;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/15/16.
 */
public class DepartmentMemoryLoader<T extends Department> implements MemoryLoaderInterface<T> {
    @SuppressWarnings("unchecked")
    @Override
    public void load(RepositoryInterface repository, int howMany) {
        for(int i = 0; i < howMany; i++) {
            repository.insert(new Department(
                    repository.getNextId(),
                    "Department" + i,
                    Integer.valueOf(RandomStringUtils.randomNumeric(3))
            ));
        }
    }
}
