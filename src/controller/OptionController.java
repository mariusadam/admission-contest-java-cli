package controller;

import domain.Candidate;
import domain.Department;
import domain.Option;
import exception.DuplicateEntryException;
import exception.InvalidEntityException;
import repository.RepositoryInterface;
import validator.ValidatorInterface;

import java.util.Collection;

/**
 * Created by marius on 10/16/16.
 */
public class OptionController {
    private RepositoryInterface<Option>     optionRepository;
    private RepositoryInterface<Candidate>  candidateRepository;
    private RepositoryInterface<Department> departmentRepository;
    private ValidatorInterface              validator;

    public OptionController(RepositoryInterface<Option> repository, RepositoryInterface<Candidate> candidateRepository, RepositoryInterface<Department> departmentRepository, ValidatorInterface validator) {
        this.optionRepository = repository;
        this.candidateRepository = candidateRepository;
        this.departmentRepository = departmentRepository;
        this.validator = validator;
    }

    public Option create(Integer candidateIt, Integer departmentId) throws InvalidEntityException, DuplicateEntryException {
        Option option = new Option(
                this.optionRepository.getLastId(),
                this.candidateRepository.findById(candidateIt),
                this.departmentRepository.findById(departmentId)
        );

        this.validator.validate(option);
        this.optionRepository.insert(option);

        return option;
    }

    public Option update(Integer optionId, String newCandidateId, String newDepartmentId) throws InvalidEntityException {
        Option option = this.optionRepository.findById(optionId);

        if (!newCandidateId.isEmpty()) {
            option.setCandidate(this.candidateRepository.findById(Integer.parseInt(newCandidateId)));
        }

        if (!newDepartmentId.isEmpty()) {
            option.setDepartment(this.departmentRepository.findById(Integer.parseInt(newDepartmentId)));
        }
        this.validator.validate(option);
        this.optionRepository.update(option);

        return option;
    }

    public Option delete(Integer id) {
        return this.optionRepository.delete(id);
    }

    public Collection<Option> getAll() {
        return this.optionRepository.getAll();
    }
}
