package helper.saver;

import repository.RepositoryInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Marius Adam
 */
public abstract class FileSaver implements FileSaverInterface{
    /**
     * {@inheritDoc}
     */
    @Override
    public void save(RepositoryInterface repository, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Object entity : repository.getAll()) {
                writer.write(this.transform(entity));
                writer.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String transform(Object object);
}
