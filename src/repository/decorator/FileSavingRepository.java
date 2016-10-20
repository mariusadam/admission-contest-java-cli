package repository.decorator;

import domain.Entity;
import exception.DuplicateEntryException;
import repository.RepositoryInterface;
import helper.saver.FileSaverInterface;

/**
 * @author Marius Adam
 */
public class FileSavingRepository<T extends Entity> extends RepositoryDecorator<T> {
    private FileSaverInterface saver;
    private String             filename;

    public FileSavingRepository(RepositoryInterface<T> repository, FileSaverInterface saver, String filename) {
        super(repository);
        this.saver = saver;
        this.filename = filename;
    }

    @Override
    public void insert(T obj) throws DuplicateEntryException {
        super.insert(obj);
        this.saveToFile();
    }

    @Override
    public T delete(Integer id) {
        T obj = super.delete(id);
        this.saveToFile();

        return  obj;
    }

    @Override
    public void update(T entity) {
        super.update(entity);
        this.saveToFile();
    }

    private void saveToFile() {
        this.saver.save(this, this.filename);
    }
}
