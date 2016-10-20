package helper.saver;

import repository.RepositoryInterface;

/**
 * @author Marius Adam
 */
public interface FileSaverInterface<T> {
    /**
     *
     * @param repository A repository object
     * @param filename   The filename to save the objects from repository
     */
    public void save(RepositoryInterface<T> repository, String filename);
}
