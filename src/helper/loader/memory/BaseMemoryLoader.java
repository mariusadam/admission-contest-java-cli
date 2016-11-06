package helper.loader.memory;

import exception.DuplicateEntryException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * @author Marius Adam
 */
abstract class BaseMemoryLoader<T> implements MemoryLoaderInterface<T> {

    @SuppressWarnings("unchecked")
    @Override
    public Collection<T> load(int howMany) {
        Collection<T> result = new ArrayList<T>();

        for(int i = 0; i < howMany; i++) {
            try {
                result.add(this.getNewEntity());
            } catch (DuplicateEntryException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public abstract T getNewEntity();

}
