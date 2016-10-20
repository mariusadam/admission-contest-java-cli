package helper.loader.memory;

import domain.Candidate;
import org.apache.commons.lang.RandomStringUtils;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/15/16.
 */
public class CandidateMemoryLoader extends BaseMemoryLoader<Candidate> {
    @Override
    public Candidate getNewEntity(RepositoryInterface<Candidate> repository) {
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
