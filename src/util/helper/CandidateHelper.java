package util.helper;

import domain.Candidate;
import domain.Entity;
import exception.CandidateException;
import org.apache.commons.lang.StringUtils;

/**
 * Created by marius on 10/13/16.
 */
public class CandidateHelper {
    /**
     *
     * @param csvString The string from which to create the entity
     * @return Entity the newly created object
     */
    public static Candidate createFromCsvString(String csvString) {
        String[] parts = StringUtils.split(csvString, ",");

        if (parts.length != 4) {
            throw new CandidateException(String.format("Cannot create a Candidate objet with csv string %s", csvString));
        }

        return new Candidate(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3]
        );
    }

    /**
     * @param obj The object to be converted to csv format
     * @return String
     */
    public static String toCsvFormat(Candidate obj) {
        return String.format(
                "%s,%s,%s,%s",
                obj.getId().toString(),
                obj.getName(),
                obj.getPhone(),
                obj.getAddress()
                );
    }
}
