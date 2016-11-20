package repository.decorator;

import domain.HasId;
import repository.RepositoryInterface;
import helper.loader.file.FileLoaderInterface;

/**
 * @author Marius Adam
 */
public class FileLoadingRepository<Id, T extends HasId<Id>> extends RepositoryDecorator<Id, T> {
    private FileLoaderInterface<T> loader;
    private String              filename;
    /**
     * @param repository The repository object to be decorated
     * @param loader
     * @param filename
     */
    public FileLoadingRepository(RepositoryInterface<Id, T> repository, FileLoaderInterface<T> loader, String filename) {
        super(repository);
        this.loader = loader;
        this.filename = filename;
        this.loadFromFile();
    }

    private void loadFromFile() {
        super.addCollection(this.loader.load(this.filename));
    }
}
