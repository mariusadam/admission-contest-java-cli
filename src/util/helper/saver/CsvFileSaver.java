package util.helper.saver;

import domain.Entity;
import repository.RepositoryInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by marius on 10/15/16.
 */
public class CsvFileSaver<T extends Entity> implements SaverInterface<T>{
    /**
     * {@inheritDoc}
     */
    @Override
    public void save(RepositoryInterface<T> repository, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (T entity : repository.getAll()) {
                writer.write(entity.toCsvFormat());
                writer.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
