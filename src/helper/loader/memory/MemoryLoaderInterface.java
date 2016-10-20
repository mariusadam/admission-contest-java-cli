package helper.loader.memory;

import domain.Entity;
import repository.RepositoryInterface;

/**
 * @author Marius Adam
 */
public interface MemoryLoaderInterface<T> {
    /**
     * Loads the specifies number of objects in repository from memory
     *
     * @param repository A repository object
     * @param howMany    The number of entities to create/loadFromMemory
     */
    public void load(RepositoryInterface<T> repository, int howMany);

    public T getNewEntity(RepositoryInterface<T> repository);

}
