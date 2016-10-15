package util.helper.saver;

import domain.Entity;
import repository.RepositoryInterface;

import java.io.IOException;

/**
 * Created by marius on 10/15/16.
 */
public interface SaverInterface<T extends Entity> {
    /**
     *
     * @param repository A repository object
     * @param filename   The filename to save the objects from repository
     */
    public void save(RepositoryInterface<T> repository, String filename);
}
