package validator;

import domain.Candidate;
import domain.Entity;
import exception.InvalidCandidateException;

/**
 *
 */
public class CandidateValidator implements ValidatorInterface{

    @Override
    public void validate(Entity obj) throws InvalidCandidateException {
        if (!(obj instanceof Candidate)) {
            throw new InvalidCandidateException("Invalid Candidate object.");
        }

        //// TODO: 10/14/16 properly validate this object too
    }
}
