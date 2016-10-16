package util.helper.loader.memory;

import domain.Entity;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/15/16.
 */
public interface MemoryLoaderInterface<T extends Entity> {
    /**
     * Loads the specifies number of objects in repository from memory
     *
     * @param repository A repository object
     * @param howMany    The number of entities to create/loadFromMemory
     */
    public void load(RepositoryInterface<T> repository, int howMany);

    public Entity getNewEntity(RepositoryInterface<T> repository);

}
