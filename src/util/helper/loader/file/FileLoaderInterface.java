package util.helper.loader.file;

import repository.RepositoryInterface;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by marius on 10/13/16.
 */
public interface FileLoaderInterface {
    /**
     * Loads objects from a csv file
     *
     * @param repository A repository object
     * @param filename   The path to the csv file
     */
    public void load(RepositoryInterface repository, String filename);
}