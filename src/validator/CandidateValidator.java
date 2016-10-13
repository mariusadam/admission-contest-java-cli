package validator;

import domain.Candidate;
import domain.Entity;
import exception.CandidateException;

/**
 *
 */
public class CandidateValidator implements ValidatorInterface{

    @Override
    public void validate(Entity obj) {
        if (!(obj instanceof Candidate)) {
            throw new CandidateException("Invalid Candidate object.");
        }

        //// TODO: 10/14/16 properly validate this object too
    }
}
