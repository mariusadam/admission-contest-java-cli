package helper.loader.file.serialized;

import exception.RepositoryException;
import helper.loader.file.FileLoaderInterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by marius on 11/7/16.
 */
public class SerializedLoader<T> implements FileLoaderInterface<T> {
    @Override
    public Collection<T> load(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){

            //noinspection unchecked
            Collection<T> result = (ArrayList<T>) ois.readObject();

            ois.close();

            return result;
        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException(e);
        }
    }
}
