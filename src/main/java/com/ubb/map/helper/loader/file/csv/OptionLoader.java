package com.ubb.map.helper.loader.file.csv;

import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.Option;
import com.ubb.map.exception.InvalidCandidateException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.validator.ValidatorInterface;

import java.util.Arrays;

/**
 * @author Marius Adam
 */
public class OptionLoader extends BaseCsvLoader<Option> {
    private RepositoryInterface<Integer, Candidate>  candidateRepository;
    private RepositoryInterface<Integer, Department> departmentRepository;

    public OptionLoader(ValidatorInterface<Option> validator, RepositoryInterface<Integer, Candidate> cr, RepositoryInterface<Integer, Department> dr) {
        super(validator);
        this.candidateRepository = cr;
        this.departmentRepository = dr;
    }

    public OptionLoader(ValidatorInterface<Option> validator, String separator, RepositoryInterface<Integer, Candidate> cr, RepositoryInterface<Integer, Department> dr) {
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
                    this.departmentRepository.findById(Integer.valueOf(parts[2]))
                    );
        }
    }
}
