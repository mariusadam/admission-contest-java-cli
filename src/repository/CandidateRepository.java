package repository;

import domain.Candidate;

import java.util.NoSuchElementException;

/**
 *
 */
public class CandidateRepository extends Repository {

    public CandidateRepository() {
        super();
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
