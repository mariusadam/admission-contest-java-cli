package util.helper.loader.memory;

import repository.RepositoryInterface;

/**
 * Created by marius on 10/15/16.
 */
public interface MemoryLoaderInterface {
    /**
     * Loads the specifies number of objects in repository from memory
     *
     * @param repository A repository object
     * @param howMany    The number of entities to create/loadFromMemory
     */
    public void load(RepositoryInterface repository, int howMany);

}
