package com.ubb.map.services;

import com.ubb.map.domain.Candidate;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.helper.generator.RandomGenerator;
import com.ubb.map.repository.db.CandidateRepository;
import com.ubb.map.validator.CandidateValidator;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 */
@Singleton
public class CandidateCrudService extends BaseCrudService<Integer, Candidate> {
    /**
     *
     * @param repository  The optionRepository class for Candidate entities
     * @param validator            The com.ubb.map.validator for the Candidate entity
     */
    @Inject
    public CandidateCrudService(
            CandidateRepository repository,
            CandidateValidator validator
    ) {
        super(repository, validator);
    }

    /**
     *
     * @param name    The name of the candidate
     * @param phone   The phone number of the candidate
     * @param address The address of the candidate
     * @return The newly created Candidate object
     */
    public Candidate create(String name, String phone, String address) throws InvalidObjectException, DuplicateEntryException {
        Candidate candidate = new Candidate(
                RandomGenerator.getRandomPositiveInt(),
                name,
                phone,
                address
        );

        this.validator.validate(candidate);
        this.repository.insert(candidate);

        return candidate;
    }

    /**
     *
     * @return The newly update Candidate object
     */
    public Candidate update(Candidate c) throws InvalidObjectException {
        return update(c.getId(), c.getName(), c.getPhone(), c.getAddress());
    }

    /**
     *
     * @param id         The id of the candidate to be updated
     * @param newName    The new name of the candidate
     * @param newPhone   The new phone of the candidate
     * @param newAddress The new address of the candidate
     * @return The newly update Candidate object
     */
    public Candidate update(Integer id, String newName, String newPhone, String newAddress) throws InvalidObjectException {
        Candidate candidate = this.repository.findById(id);

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


    public Collection<Candidate> filterByName(String name) {
        return this.getAll().stream().filter(candidate -> candidate.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
}
