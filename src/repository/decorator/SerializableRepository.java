package repository.decorator;

import domain.HasId;
import exception.*;
import repository.RepositoryInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by marius on 11/4/16.
 */
public class SerializableRepository<Id, T extends HasId<Id>> extends RepositoryDecorator<Id, T> {
    private String             filename;

    public SerializableRepository(RepositoryInterface<Id, T> repository, String filename) {
        super(repository);
        this.filename = filename;
        this.deserialize();
    }

    @Override
    public void insert(T obj) throws DuplicateEntryException {
        super.insert(obj);
        this.serialize();
    }

    @Override
    public T delete(Id id) {
        T ret = super.delete(id);
        this.serialize();

        return ret;
    }

    @Override
    public void update(T entity) {
        super.update(entity);
        this.serialize();
    }

    @Override
    public void addCollection(Collection<T> collection) {
        super.addCollection(collection);
        this.serialize();
    }

    private void deserialize() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){

            //noinspection unchecked
            super.addCollection((ArrayList<T>) ois.readObject());

            ois.close();
        } catch (IOException | ClassNotFoundException | DuplicateEntryException e) {
            throw new RepositoryException(e);
        }

    }

    private void serialize() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {

            ArrayList<T> list = new ArrayList<>();
            list.addAll(super.getAll());
            oos.writeObject(list);
            oos.close();

        } catch (IOException e) {
            throw new RepositoryException(e);
        }
    }
}
