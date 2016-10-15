package repository.decorator;

import domain.Entity;
import repository.RepositoryInterface;
import util.helper.saver.SaverInterface;

/**
 * Created by marius on 10/15/16.
 */
public class FileSavingRepository<T extends Entity> extends RepositoryDecorator<T> {
    protected SaverInterface<T> saver;
    protected String            filename;

    public FileSavingRepository(RepositoryInterface<T> repository, SaverInterface<T> saver, String filename) {
        super(repository);
        this.saver = saver;
        this.filename = filename;
    }

    @Override
    public void insert(T obj) {
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
