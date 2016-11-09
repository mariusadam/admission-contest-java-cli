package helper.loader.memory;

import java.util.Collection;

/**
 * @author Marius Adam
 */
public interface MemoryLoaderInterface<T> {
    /**
     * Loads the specifies number of objects in repository from memory
     *
     * @param howMany    The number of entities to create/loadFromMemory
     */
    Collection<T> load(int howMany);
}
