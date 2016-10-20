package helper.saver;

import repository.RepositoryInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Marius Adam
 */
public abstract class FileSaver<T> implements FileSaverInterface<T>{
    /**
     * {@inheritDoc}
     */
    @Override
    public void save(RepositoryInterface<T> repository, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Object entity : repository.getAll()) {
                writer.write(this.transform(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String transform(Object object);
}
