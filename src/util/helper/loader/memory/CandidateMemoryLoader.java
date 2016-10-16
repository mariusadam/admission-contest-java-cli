package util.helper.loader.memory;

import domain.Candidate;
import domain.Department;
import domain.Entity;
import exception.DuplicateEntryException;
import org.apache.commons.lang.RandomStringUtils;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/15/16.
 */
public class CandidateMemoryLoader<T extends Candidate> extends BaseMemoryLoader<T> {
    @Override
    public Candidate getNewEntity(RepositoryInterface<T> repository) {
        Integer lastId = repository.getNextId();
        return new Candidate(
                lastId,
                "Candidate" + lastId,
                RandomStringUtils.randomNumeric(10),
                "Street " + RandomStringUtils.randomAlphabetic(10) +
                        " - nr " + RandomStringUtils.randomNumeric(3) +
                        " - City " + RandomStringUtils.randomAlphabetic(6)
        );
    }
}
