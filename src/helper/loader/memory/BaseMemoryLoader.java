package helper.loader.memory;

import domain.Entity;
import exception.DuplicateEntryException;
import repository.RepositoryInterface;

/**
 * @author Marius Adam
 */
abstract class BaseMemoryLoader<T> implements MemoryLoaderInterface<T> {
    @SuppressWarnings("unchecked")
    @Override
    public void load(RepositoryInterface<T> repository, int howMany) {
        for(int i = 0; i < howMany; i++) {
            try {
                repository.insert(this.getNewEntity(repository));
            } catch (DuplicateEntryException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract T getNewEntity(RepositoryInterface<T> repository);

}
