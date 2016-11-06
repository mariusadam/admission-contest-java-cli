package helper.loader.memory;

import domain.Candidate;
import helper.Generator.RandomGenerator;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Random;

/**
 * Created by marius on 10/15/16.
 */
public class CandidateMemoryLoader extends BaseMemoryLoader<Candidate> {

    @Override
    public Candidate getNewEntity() {
        int id = Math.abs(RandomGenerator.getRandomInt());

        return new Candidate(
                id,
                "Candidate" + id,
                RandomStringUtils.randomNumeric(10),
                "Street " + RandomStringUtils.randomAlphabetic(10) +
                        " - nr " + RandomStringUtils.randomNumeric(3) +
                        " - City " + RandomStringUtils.randomAlphabetic(6)
        );
    }
}
