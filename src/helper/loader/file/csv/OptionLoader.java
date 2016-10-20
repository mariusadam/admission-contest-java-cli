package helper.loader.file.csv;

import domain.Candidate;
import domain.Department;
import domain.Option;
import exception.InvalidCandidateException;
import helper.loader.file.BaseFileLoader;
import repository.RepositoryInterface;
import validator.ValidatorInterface;

import java.util.Arrays;

/**
 * @author Marius Adam
 */
public class OptionLoader extends BaseCsvLoader<Option> {
    private RepositoryInterface<Candidate>  candidateRepository;
    private RepositoryInterface<Department> departmentRepository;

    public OptionLoader(ValidatorInterface<Option> validator, String separator, RepositoryInterface<Candidate> cr, RepositoryInterface<Department> dr) {
        super(validator, separator);
        this.candidateRepository = cr;
        this.departmentRepository = dr;
    }

    /**
     *
     * @param  parts   The properties from which to create the entity
     * @return Entity The newly created object
     */
    public Option createFromArray(String[] parts) throws InvalidCandidateException {
        if(parts.length != 3) {
            throw new InvalidCandidateException(String.format("Cannot create a Option object with csv string %s", Arrays.toString(parts)));
        } else {
            return new Option(
                    Integer.parseInt(parts[0]),
                    this.candidateRepository.findById(Integer.parseInt(parts[1])),
                    this.departmentRepository.findById(Integer.parseInt(parts[2]))
                    );
        }
    }
}
