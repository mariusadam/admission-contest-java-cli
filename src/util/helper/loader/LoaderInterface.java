package util.helper.loader;

import repository.RepositoryInterface;

/**
 * Created by marius on 10/13/16.
 */
public interface LoaderInterface {
    /**
     *
     * @param repository A repository object
     * @param howMany    The number of entities to create/load
     */
    public void load(RepositoryInterface repository, int howMany);
}
