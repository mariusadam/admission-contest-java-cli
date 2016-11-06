package helper.saver;

import java.util.Collection;

/**
 * @author Marius Adam
 */
public interface FileSaverInterface<T> {
    /**
     * @param items A repository object
     * @param filename   The filename to save the objects from repository
     */
    void save(Collection<T> items, String filename);
}
