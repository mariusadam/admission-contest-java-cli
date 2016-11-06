package controller;

import domain.Candidate;
import domain.Department;
import domain.Option;
import exception.DuplicateEntryException;
import exception.InvalidObjectException;
import repository.RepositoryInterface;
import validator.ValidatorInterface;

import java.util.Collection;

/**
 * Created by marius on 10/16/16.
 */
public class OptionController {
    private RepositoryInterface<Integer, Option>     optionRepository;
    private RepositoryInterface<Integer, Candidate>  candidateRepository;
    private RepositoryInterface<String, Department> departmentRepository;
    private ValidatorInterface<Option>      validator;

    public OptionController(
            RepositoryInterface<Integer, Option>     repository,
            RepositoryInterface<Integer, Candidate>  candidateRepository,
            RepositoryInterface<String, Department> departmentRepository,
            ValidatorInterface<Option>      validator
    )
    {
        this.optionRepository = repository;
        this.candidateRepository = candidateRepository;
        this.departmentRepository = departmentRepository;
        this.validator = validator;
    }

    public Option create(String cid, String departmentId) throws InvalidObjectException, DuplicateEntryException {
        Integer candidateIt = null;

        try {
            if (!cid.isEmpty()) {
                candidateIt = Integer.parseInt(cid);
            }
        }catch (NumberFormatException e) {
            throw new InvalidObjectException("Invalid id: " + e.getMessage());
        }

        Option option = new Option(
                this.optionRepository.getNextId(),
                this.candidateRepository.findById(candidateIt),
                this.departmentRepository.findById(departmentId)
        );

        this.validator.validate(option);
        this.optionRepository.insert(option);

        return option;
    }

    public Option update(Integer optionId, String newCandidateId, String newDepartmentId) throws InvalidObjectException {
        Option option = this.optionRepository.findById(optionId);

        if (!newCandidateId.isEmpty()) {
            option.setCandidate(this.candidateRepository.findById(Integer.parseInt(newCandidateId)));
        }

        if (!newDepartmentId.isEmpty()) {
            option.setDepartment(this.departmentRepository.findById(newDepartmentId));
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
