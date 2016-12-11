package com.ubb.map.repository.decorator;

import com.ubb.map.domain.HasId;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.helper.loader.file.FileLoaderInterface;

/**
 * @author Marius Adam
 */
public class FileLoadingRepository<Id, T extends HasId<Id>> extends RepositoryDecorator<Id, T> {
    private FileLoaderInterface<T> loader;
    private String              filename;
    /**
     * @param repository The com.ubb.map.repository object to be decorated
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
