package util.helper.loader;

import domain.Department;
import org.apache.commons.lang.RandomStringUtils;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/13/16.
 */
public class DepartmentLoader implements LoaderInterface {
    /**
     * @param repository A repository object
     * @param howMany    The number of entities to create/load
     */
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
