package repository.decorator;

import domain.Entity;
import exception.DuplicateEntryException;
import repository.RepositoryInterface;

import java.util.Collection;

/**
 * @author Marius Adam
 */
abstract class RepositoryDecorator<T> implements RepositoryInterface<T> {
    protected final RepositoryInterface<T> repository;

    /**
     *
     * @param repository The repository object to be decorated
     */
    RepositoryDecorator(RepositoryInterface<T> repository) {
        this.repository = repository;
    }

    @Override
    public void insert(T obj) throws DuplicateEntryException {
        this.repository.insert(obj);
    }

    @Override
    public T delete(Integer id) {
        return this.repository.delete(id);
    }

    @Override
    public void update(T entity) {
        this.repository.update(entity);
    }

    @Override
    public T findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public Collection<T> getAll() {
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
