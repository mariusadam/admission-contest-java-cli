package com.ubb.map.services;

import com.ubb.map.domain.Candidate;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.helper.generator.RandomGenerator;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.validator.ValidatorInterface;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 *
 */
public class CandidateCrudService {
    private RepositoryInterface<Integer, Candidate> candidateRepository;
    private ValidatorInterface<Candidate>  validator;

    /**
     *
     * @param candidateRepository  The optionRepository class for Candidate entities
     * @param validator            The com.ubb.map.validator for the Candidate entity
     */
    public CandidateCrudService(RepositoryInterface<Integer, Candidate> candidateRepository, ValidatorInterface<Candidate> validator) {
        this.candidateRepository = candidateRepository;
        this.validator = validator;
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
        this.candidateRepository.insert(candidate);

        return candidate;
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
        Candidate candidate = this.candidateRepository.findById(id);

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
        this.candidateRepository.update(candidate);

        return candidate;
    }

    /**
     *
     * @param id                      The id of the candidate to be deleted
     * @return {@link Candidate}      The deleted candidate
     * @throws NoSuchElementException If the candidate with given id is not found
     */
    public Candidate delete(Integer id) {
        return this.candidateRepository.delete(id);
    }

    public Collection<Candidate> getAll() {
        return this.candidateRepository.getAll();
    }

    public Collection<Candidate> filterByName(String name) {
        return this.getAll().stream().filter(candidate -> candidate.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
    }
}
