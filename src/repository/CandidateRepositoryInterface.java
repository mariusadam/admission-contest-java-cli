package repository;

import domain.Candidate;

/**
 * Created by marius on 10/15/16.
 */
public interface CandidateRepositoryInterface extends RepositoryInterface {
    @Override
    Candidate delete(Integer id);

    @Override
    Candidate findById(Integer id);

    @Override
    Candidate[] getAll();
}
