package repository.decorator;

import domain.Entity;
import repository.RepositoryInterface;
import helper.loader.file.FileLoaderInterface;

/**
 * @author Marius Adam
 */
public class FileLoadingRepository<T extends Entity> extends RepositoryDecorator<T> {
    private FileLoaderInterface loader;
    private String                 filename;
    /**
     * @param repository The repository object to be decorated
     * @param loader
     * @param filename
     */
    public FileLoadingRepository(RepositoryInterface<T> repository, FileLoaderInterface loader, String filename) {
        super(repository);
        this.loader = loader;
        this.filename = filename;
        this.loadFromFile();
    }

    private void loadFromFile() {
        this.loader.load(this, this.filename);
    }
}
