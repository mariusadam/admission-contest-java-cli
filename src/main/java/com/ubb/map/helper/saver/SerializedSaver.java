package com.ubb.map.helper.saver;

import com.ubb.map.exception.SaverException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by marius on 11/7/16.
 */
public class SerializedSaver<T> implements FileSaverInterface<T> {
    @Override
    public void save(Collection<T> items, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            ArrayList<T> list = new ArrayList<>(items);

            oos.writeObject(list);
            oos.close();
        } catch (IOException e) {
            throw new SaverException(e);
        }
    }
}
