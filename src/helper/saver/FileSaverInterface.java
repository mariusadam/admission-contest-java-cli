package helper.saver;

import repository.RepositoryInterface;

/**
 * @author Marius Adam
 */
public interface FileSaverInterface {
    /**
     *
     * @param repository A repository object
     * @param filename   The filename to save the objects from repository
     */
    public void save(RepositoryInterface repository, String filename);
}
