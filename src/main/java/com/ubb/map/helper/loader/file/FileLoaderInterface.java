package com.ubb.map.helper.loader.file;

import java.util.Collection;

/**
 * @author Marius Adam
 */
public interface FileLoaderInterface<T>{
    /**
     * Loads objects from a csv file
     *
     * @param filename   The path to the csv file
     */
    public Collection<T> load(String filename);
}