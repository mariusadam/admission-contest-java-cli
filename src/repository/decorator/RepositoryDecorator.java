package repository.decorator;

import domain.HasId;
import exception.DuplicateEntryException;
import repository.RepositoryInterface;

import java.util.Collection;

/**
 * @author Marius Adam
 */
public abstract class RepositoryDecorator<Id, T extends HasId<Id>> implements RepositoryInterface<Id, T> {
    protected final RepositoryInterface<Id, T> repository;

    /**
     *
     * @param repository The repository object to be decorated
     */
    RepositoryDecorator(RepositoryInterface<Id, T> repository) {
        this.repository = repository;
    }

    @Override
    public void insert(T obj) throws DuplicateEntryException {
        this.repository.insert(obj);
    }

    @Override
    public T delete(Id id) {
        return this.repository.delete(id);
    }

    @Override
    public void update(T entity) {
        this.repository.update(entity);
    }

    @Override
    public T findById(Id id) {
        return this.repository.findById(id);
    }

    @Override
    public Collection<T> getAll() {
        return this.repository.getAll();
    }

   @Override
    public int size() {
        return this.repository.size();
    }

    @Override
    public void addCollection(Collection<T> collection) {
        this.repository.addCollection(collection);
    }
}
