package helper.loader.file;

import repository.RepositoryInterface;

/**
 * @author Marius Adam
 */
public interface FileLoaderInterface<T>{
    /**
     * Loads objects from a csv file
     *
     * @param repository A repository object
     * @param filename   The path to the csv file
     */
    public void load(RepositoryInterface<T> repository, String filename);
}