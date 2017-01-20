package com.ubb.map.services.crud;

import com.ubb.map.domain.Candidate;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.CandidateRepository;
import com.ubb.map.validator.CandidateValidator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.SQLException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 */
@Singleton
public class CandidateCrudService extends BaseCrudService<Integer, Candidate> {
    private CandidateRepository repository;

    /**
     * @param repository The optionRepository class for Candidate entities
     * @param validator  The com.ubb.map.validator for the Candidate entity
     */
    @Inject
    public CandidateCrudService(
            CandidateRepository repository,
            CandidateValidator validator
    ) {
        super(validator);
        this.repository = repository;
    }

    /**
     * @param name    The name of the candidate
     * @param phone   The phone number of the candidate
     * @param address The address of the candidate
     * @return The newly created Candidate object
     */
    public Candidate create(String name, String phone, String address) throws InvalidObjectException, DuplicateEntryException, SQLException {
        Candidate candidate = new Candidate(
                name,
                phone,
                address
        );

        this.validator.validate(candidate);
        this.repository.insert(candidate);

        return candidate;
    }

    /**
     * @return The newly update Candidate object
     */
    public Candidate update(Candidate c) throws InvalidObjectException, SQLException, RepositoryException {
        return update(c.getId(), c.getName(), c.getPhone(), c.getAddress());
    }

    /**
     * @param realId     The id of the candidate to be updated
     * @param newName    The new name of the candidate
     * @param newPhone   The new phone of the candidate
     * @param newAddress The new address of the candidate
     * @return The newly update Candidate object
     */
    public Candidate update(Integer realId, String newName, String newPhone, String newAddress) throws SQLException, RepositoryException, InvalidObjectException {
        Candidate candidate = this.repository.findById(realId);

        if (!newName.isEmpty()) {
            candidate.setName(newName);
        }
        if (!newPhone.isEmpty()) {
            candidate.setPhone(newPhone);
        }
        if (!newAddress.isEmpty()) {
            candidate.setAddress(newAddress);
        }
        this.validator.validate(candidate);
        this.repository.update(candidate);

        return candidate;
    }

    /**
     * @param id         The id of the candidate to be updated
     * @param newName    The new name of the candidate
     * @param newPhone   The new phone of the candidate
     * @param newAddress The new address of the candidate
     * @return The newly update Candidate object
     */
    public Candidate update(String id, String newName, String newPhone, String newAddress) throws InvalidObjectException, SQLException, RepositoryException {
        Integer realId;
        try {
            realId = Integer.parseInt(id);
        } catch (NumberFormatException ignored) {
            realId = null;
        }

        return update(realId, newName, newPhone, newAddress);
    }


    public Collection<Candidate> filterByName(String name) throws SQLException {
        return this.getAll().stream().filter(candidate -> candidate.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    protected RepositoryInterface<Integer, Candidate> getRepository() {
        return repository;
    }
}
