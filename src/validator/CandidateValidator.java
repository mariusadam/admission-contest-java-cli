package validator;

import domain.Candidate;
import domain.Entity;
import exception.InvalidCandidateException;

/**
 *
 */
public class CandidateValidator implements ValidatorInterface<Candidate>{

    @Override
    public void validate(Candidate obj) throws InvalidCandidateException {
        if (!(obj instanceof Candidate)) {
            throw new InvalidCandidateException("Invalid Candidate object.");
        }

        //// TODO: 10/14/16 properly validate this object too
    }
}
