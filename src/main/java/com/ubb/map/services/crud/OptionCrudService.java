package com.ubb.map.services.crud;

import com.ubb.map.domain.Department;
import com.ubb.map.domain.Option;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.CandidateRepository;
import com.ubb.map.repository.db.DepartmentRepository;
import com.ubb.map.repository.db.OptionRepository;
import com.ubb.map.services.filters.PropertyFilter;
import com.ubb.map.validator.OptionValidator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by marius on 10/16/16.
 */
@Singleton
public class OptionCrudService extends BaseCrudService<Integer, Option> {
    private OptionRepository repository;
    private CandidateRepository candidateRepository;
    private DepartmentRepository departmentRepository;

    @Inject
    public OptionCrudService(
            OptionRepository optionRepository,
            CandidateRepository candidateRepository,
            DepartmentRepository departmentRepository,
            OptionValidator validator
    ) {
        super(validator);
        this.repository = optionRepository;
        this.candidateRepository = candidateRepository;
        this.departmentRepository = departmentRepository;
    }

    public Option create(String cid, String departmentId) throws InvalidObjectException, DuplicateEntryException, SQLException, RepositoryException {
        Option option = new Option(
                candidateRepository.findById(getIntOrNull(cid)),
                departmentRepository.findById(getIntOrNull(departmentId))
        );

        this.validator.validate(option);
        this.repository.insert(option);

        return option;
    }

    public Option update(String optionId, String newCandidateId, String newDepartmentId, String newCode) throws InvalidObjectException, SQLException, RepositoryException {
        Option option = this.repository.findById(getIntOrNull(optionId));

        if (!newCandidateId.isEmpty()) {
            option.setCandidate(this.candidateRepository.findById(getIntOrNull(newCandidateId)));
        }
        Department departmentById = null, departmentByCode = null, finalDepartment = null;
        if (!newDepartmentId.isEmpty()) {
            departmentById = departmentRepository.findById(getIntOrNull(newDepartmentId));
        }
        if (!newCode.isEmpty()) {
            departmentByCode = departmentRepository.findByCode(newCode);
        }

        if (departmentById != null && departmentByCode != null && !departmentById.equals(departmentByCode)) {
            throw new InvalidObjectException("The code supplied does not match with the id.");
        }
        if (departmentByCode != null) {
            finalDepartment = departmentByCode;
        } else if (departmentById != null) {
            finalDepartment = departmentById;
        }

        if (finalDepartment != null) {
            option.setDepartment(finalDepartment);
        }

        this.validator.validate(option);
        this.repository.update(option);

        return option;
    }

    public CandidateRepository getCandidateRepository() {
        return candidateRepository;
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepository;
    }

    @Override
    public Collection<Option> getFiltered(List<PropertyFilter> filters, int page, int perPage) throws SQLException, RepositoryException {
        Collection<Option> options = super.getFiltered(filters, page, perPage);
        hydrateFull(options);

        return options;
    }

    @Override
    protected void hydrateFull(Collection<Option> options) throws SQLException, RepositoryException {
        for (Option option : options) {
            if (!option.isHydrated()) {
                option.setCandidate(candidateRepository.findById(option.getCandidate().getId()));
                option.setDepartment(departmentRepository.findById(option.getDepartment().getId()));
                option.setHydrated();
            }
        }
    }

    @Override
    protected RepositoryInterface<Integer, Option> getRepository() {
        return repository;
    }
}
