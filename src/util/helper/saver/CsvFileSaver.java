package util.helper.saver;

import domain.Entity;
import repository.RepositoryInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by marius on 10/15/16.
 */
public class CsvFileSaver implements SaverInterface{
    /**
     * {@inheritDoc}
     */
    @Override
    public void save(RepositoryInterface repository, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Entity entity : repository.getAll()) {
                writer.write(entity.toCsvFormat());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
