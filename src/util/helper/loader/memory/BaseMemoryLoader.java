package util.helper.loader.memory;

import domain.Department;
import domain.Entity;
import exception.DuplicateEntryException;
import org.apache.commons.lang.RandomStringUtils;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/16/16.
 */
public abstract class BaseMemoryLoader<T extends Entity> implements MemoryLoaderInterface<T> {
    @SuppressWarnings("unchecked")
    @Override
    public void load(RepositoryInterface repository, int howMany) {
        for(int i = 0; i < howMany; i++) {
            try {
                repository.insert(this.getNewEntity(repository));
            } catch (DuplicateEntryException e) {
                e.printStackTrace();
            }
        }
    }
}
