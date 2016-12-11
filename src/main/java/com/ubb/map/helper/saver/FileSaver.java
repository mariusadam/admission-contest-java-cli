package com.ubb.map.helper.saver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Marius Adam
 */
public abstract class FileSaver<T> implements FileSaverInterface<T>{
    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Collection<T> items, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (T entity : items) {
                writer.write(this.transform(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String transform(T object);
}
