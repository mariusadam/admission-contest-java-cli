package repository;

import domain.Candidate;
import domain.Entity;
import exception.CandidateException;

import java.util.NoSuchElementException;

/**
 *
 */
public class CandidateRepository extends Repository {

    public CandidateRepository() {
        super();
    }

    /**
     * Inserts a new entity into the repository
     *
     * @param obj The object to be inserted
     * @throws exception.DuplicateIdException If there is already an entity with the same id
     */
    @Override
    public void insert(Entity obj) {
        if (!(obj instanceof Candidate)) {
            throw new CandidateException("Inserted objects must be of type Candidate.");
        }

        super.insert(obj);
    }

    /**
     * Removes the entity from the repository
     *
     * @param id The id of the entity to be deleted
     * @return Entity The deleted entity
     */
    @Override
    public Candidate delete(Integer id) {
        return (Candidate) super.delete(id);
    }

    /**
     * Searches for an candidate with a given id
     *
     * @param id The id of the searched entity
     * @return Candidate The searched entity
     * @throws NoSuchElementException If the searched candidate is not found
     */
    @Override
    public Candidate findById(Integer id) {
        return (Candidate) super.findById(id);
    }
}
