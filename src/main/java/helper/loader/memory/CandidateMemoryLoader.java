package helper.loader.memory;

import domain.Candidate;
import helper.generator.RandomGenerator;
import org.apache.commons.lang.RandomStringUtils;

/**
 * Created by marius on 10/15/16.
 */
public class CandidateMemoryLoader extends BaseMemoryLoader<Candidate> {

    @Override
    public Candidate getNewEntity() {

        return new Candidate(
                RandomGenerator.getRandomString(10) + RandomGenerator.getRandomString(10),
                RandomStringUtils.randomNumeric(10),
                "Street " + RandomStringUtils.randomAlphabetic(10) +
                        " - nr " + RandomStringUtils.randomNumeric(3) +
                        " - City " + RandomStringUtils.randomAlphabetic(6)
        );
    }
}
