package helper.loader.file.csv;

import domain.Candidate;
import exception.InvalidCandidateException;
import helper.loader.file.BaseFileLoader;
import validator.ValidatorInterface;

import java.util.Arrays;

/**
 * @author Marius Adam
 */
public class CandidateLoader extends BaseCsvLoader<Candidate> {
    public CandidateLoader(ValidatorInterface<Candidate> validator) {
        super(validator);
    }

    public CandidateLoader(ValidatorInterface<Candidate> validator, String propertySeparator) {
        super(validator, propertySeparator);
    }

    /**
     *
     * @param parts   The properties from which to create the entity
     * @return Entity The newly created object
     */
    public Candidate createFromArray(String[] parts) throws InvalidCandidateException {
        if(parts.length != 4) {
            throw new InvalidCandidateException(String.format("Cannot create a Candidate object with csv string %s", Arrays.toString(parts)));
        } else {
            return new Candidate(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
        }
    }
}
