package util.helper.loader.file;

import domain.Candidate;
import exception.InvalidCandidateException;
import org.apache.commons.lang.StringUtils;

/**
 * Created by marius on 10/13/16.
 */
public class CandidateCsvLoader<T extends Candidate> extends BaseFileLoader<T> {
    /**
     *
     * @param csvString The string from which to create the entity
     * @return Entity the newly created object
     */
    public Candidate createFromFormat(String csvString) throws InvalidCandidateException {
        String[] parts = StringUtils.split(csvString, ",");
        if(parts.length != 4) {
            throw new InvalidCandidateException(String.format("Cannot create a Candidate object with csv string %s", new Object[]{csvString}));
        } else {
            return new Candidate(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
        }
    }
}
