package repository.decorator;

import domain.Entity;
import repository.RepositoryInterface;
import util.helper.loader.file.FileLoaderInterface;

/**
 * Created by marius on 10/15/16.
 */
public class FileLoadingRepository<T extends Entity> extends RepositoryDecorator<T> {
    private FileLoaderInterface<T> loader;
    private String                 filename;
    /**
     * @param repository The repository object to be decorated
     * @param loader
     * @param filename
     */
    public FileLoadingRepository(RepositoryInterface<T> repository, FileLoaderInterface<T> loader, String filename) {
        super(repository);
        this.loader = loader;
        this.filename = filename;
    }

    private void loadFromFile() {
        this.loader.load(this, this.filename);
    }
}
