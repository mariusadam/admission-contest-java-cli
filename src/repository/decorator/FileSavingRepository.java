package repository.decorator;

import domain.Entity;
import repository.RepositoryInterface;
import util.helper.saver.SaverInterface;

/**
 * Created by marius on 10/15/16.
 */
public class FileSavingRepository extends RepositoryDecorator {
    protected SaverInterface saver;
    protected String         filename;

    public FileSavingRepository(RepositoryInterface repository, SaverInterface saver, String filename) {
        super(repository);
        this.saver = saver;
        this.filename = filename;
    }

    @Override
    public void insert(Entity obj) {
        super.insert(obj);
        this.saveToFile();
    }

    @Override
    public Entity delete(Integer id) {
        Entity obj = super.delete(id);
        this.saveToFile();

        return  obj;
    }

    @Override
    public void update(Entity entity) {
        super.update(entity);
        this.saveToFile();
    }

    private void saveToFile() {
        this.saver.save(this, this.filename);
    }
}
