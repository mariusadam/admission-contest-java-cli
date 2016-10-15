package repository.decorator;

import domain.Candidate;
import domain.Entity;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/15/16.
 */
public abstract class RepositoryDecorator implements RepositoryInterface {
    protected final RepositoryInterface repository;

    /**
     *
     * @param repository The repository object to be decorated
     */
    public RepositoryDecorator(RepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public void insert(Entity obj) {
        this.repository.insert(obj);
    }

    @Override
    public Entity delete(Integer id) {
        return this.repository.delete(id);
    }

    @Override
    public void update(Entity entity) {
        this.repository.update(entity);
    }

    @Override
    public Entity findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public Entity[] getAll() {
        return this.repository.getAll();
    }

    @Override
    public Integer getLastId() {
        return this.repository.getLastId();
    }

    @Override
    public Integer getNextId() {
        return this.repository.getNextId();
    }
}
