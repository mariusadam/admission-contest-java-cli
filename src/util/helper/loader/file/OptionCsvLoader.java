package util.helper.loader.file;

import domain.Candidate;
import domain.Department;
import domain.Option;
import exception.InvalidCandidateException;
import org.apache.commons.lang.StringUtils;
import repository.RepositoryInterface;

/**
 * Created by marius on 10/16/16.
 */
public class OptionCsvLoader<T extends Option> extends BaseFileLoader<T> {
    private RepositoryInterface<Candidate>  candidateRepository;
    private RepositoryInterface<Department> departmentRepository;

    public OptionCsvLoader(RepositoryInterface<Candidate> candidateRepository, RepositoryInterface<Department> departmentRepository) {
        this.candidateRepository = candidateRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     *
     * @param csvString The string from which to create the entity
     * @return Entity the newly created object
     */
    public Option createFromFormat(String csvString) throws InvalidCandidateException {
        String[] parts = StringUtils.split(csvString, ",");
        if(parts.length != 3) {
            throw new InvalidCandidateException(String.format("Cannot create a Option object with csv string %s", csvString));
        } else {
            return new Option(
                    Integer.parseInt(parts[0]),
                    this.candidateRepository.findById(Integer.parseInt(parts[1])),
                    this.departmentRepository.findById(Integer.parseInt(parts[2]))
                    );
        }
    }
}
