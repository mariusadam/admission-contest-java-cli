package repository.decorator;

import domain.Candidate;
import domain.Entity;
import exception.CandidateException;
import repository.CandidateRepositoryInterface;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/15/16.
 */
public class CandidateRepositoryDecorator extends RepositoryDecorator implements CandidateRepositoryInterface{
    /**
     *
     * @param repository
     */
    public CandidateRepositoryDecorator(RepositoryInterface repository) {
        super(repository);
    }

    @Override
    public void insert(Entity obj) {
        if (!(obj instanceof Candidate)) {
            throw new CandidateException("Inserted objects must be of type Candidate.");
        }

        super.insert(obj);
    }

    @Override
    public Candidate delete(Integer id) {
        return (Candidate) super.delete(id);
    }

    @Override
    public Candidate findById(Integer id) {
        return (Candidate) super.findById(id);
    }

    @Override
    public Candidate[] getAll() {
        return (Candidate[]) super.getAll();
    }
}
