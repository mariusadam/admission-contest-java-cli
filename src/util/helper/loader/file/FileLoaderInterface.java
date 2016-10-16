package util.helper.loader.file;

import domain.Candidate;
import domain.Entity;
import exception.InvalidCandidateException;
import exception.InvalidEntityException;
import repository.RepositoryInterface;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by marius on 10/13/16.
 */
public interface FileLoaderInterface<T extends Entity> {
    /**
     * Loads objects from a csv file
     *
     * @param repository A repository object
     * @param filename   The path to the csv file
     */
    public void load(RepositoryInterface<T> repository, String filename);

    /**
     *
     * @param format  The string from which to create the entity
     * @return Entity the newly created object
     */
    public Entity createFromFormat(String format) throws InvalidEntityException;
}