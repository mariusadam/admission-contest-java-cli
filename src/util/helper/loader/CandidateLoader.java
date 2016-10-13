package util.helper.loader;

import domain.Candidate;
import org.apache.commons.lang.RandomStringUtils;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/13/16.
 */
public class CandidateLoader implements LoaderInterface {
    /**
     * @param repository A repository object
     * @param howMany    The number of entities to create/load
     */
    @Override
    public void load(RepositoryInterface repository, int howMany) {
        for(int i = 0; i < howMany; i++) {
            repository.insert(new Candidate(
                    repository.getNextId(),
                    "Candidate" + i,
                    RandomStringUtils.randomNumeric(10),
                    "Street " + RandomStringUtils.randomAlphabetic(10) +
                            ", nr " + RandomStringUtils.randomNumeric(3) +
                            ", City " + RandomStringUtils.randomAlphabetic(6)
            ));
        }
    }
}
