package repository.decorator;

import domain.HasId;
import exception.DuplicateEntryException;
import repository.RepositoryInterface;
import helper.saver.FileSaverInterface;

import java.util.Collection;

/**
 * @author Marius Adam
 */
public class FileSavingRepository<Id, T extends HasId<Id>> extends RepositoryDecorator<Id, T> {
    private FileSaverInterface<T> saver;
    private String                filename;

    public FileSavingRepository(RepositoryInterface<Id, T> repository, FileSaverInterface<T> saver, String filename) {
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
    public T delete(Id id) {
        T obj = super.delete(id);
        this.saveToFile();

        return  obj;
    }

    @Override
    public void update(T entity) {
        super.update(entity);
        this.saveToFile();
    }

    @Override
    public void addCollection(Collection<T> collection) {
        super.addCollection(collection);
        this.saveToFile();
    }

    private void saveToFile() {
        this.saver.save(this.getAll(), this.filename);
    }
}
