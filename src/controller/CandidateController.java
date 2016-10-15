package controller;

import domain.Candidate;
import repository.RepositoryInterface;
import validator.ValidatorInterface;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 *
 */
public class CandidateController {
    private RepositoryInterface<Candidate> candidateRepository;
    private ValidatorInterface             validator;

    /**
     *
     * @param candidateRepository  The repository class for Candidate entities
     * @param validator            The validator for the Candidate entity
     */
    public CandidateController(RepositoryInterface<Candidate> candidateRepository, ValidatorInterface validator) {
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
    public Candidate create(String name, String phone, String address) {
        Candidate candidate = new Candidate(
                this.candidateRepository.getNextId(),
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
    public Candidate update(Integer id, String newName, String newPhone, String newAddress) {
        Candidate candidate = this.candidateRepository.findById(id);

        if (!newName.isEmpty()) {
            candidate.setName(newName);
        }
        if (!newPhone.isEmpty()) {
            candidate.setPhone(newPhone);
        }
        if (!newAddress.isEmpty()) {
            candidate.setName(newAddress);
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
}
