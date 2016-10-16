package util.helper.loader.file;

import domain.Entity;
import exception.DuplicateEntryException;
import exception.InvalidEntityException;
import repository.RepositoryInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by marius on 10/16/16.
 */
public abstract class BaseFileLoader<T extends Entity> implements FileLoaderInterface<T> {
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void load(RepositoryInterface repository, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = reader.readLine()) != null) {
                repository.insert(createFromFormat(line));
            }
        } catch (IOException | DuplicateEntryException | InvalidEntityException e) {
            e.printStackTrace();
        }
    }
}
