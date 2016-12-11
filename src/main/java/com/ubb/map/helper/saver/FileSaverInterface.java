package com.ubb.map.helper.saver;

import java.util.Collection;

/**
 * @author Marius Adam
 */
public interface FileSaverInterface<T> {
    /**
     * @param items A com.ubb.map.repository object
     * @param filename   The filename to save the objects from com.ubb.map.repository
     */
    void save(Collection<T> items, String filename);
}
