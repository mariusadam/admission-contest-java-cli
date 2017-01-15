package com.ubb.map.validator;

import com.ubb.map.domain.Candidate;
import com.ubb.map.exception.InvalidCandidateException;

import javax.inject.Singleton;

/**
 *
 */
@Singleton
public class CandidateValidator implements ValidatorInterface<Candidate>{

    @Override
    public void validate(Candidate obj) throws InvalidCandidateException {
        if (obj == null) {
            throw new InvalidCandidateException("Invalid Candidate object.");
        }

        StringBuilder sb = new StringBuilder();

        if (obj.getId() < 0) {
            sb.append("     Invalid id: ").append(obj.getId()).append('\n');
        }

        if (obj.getName() == null || obj.getName().isEmpty()) {
            sb.append("     Invalid name: ").append(obj.getName()).append('\n');
        }

        if (obj.getAddress() == null || obj.getAddress().isEmpty()) {
            sb.append("     Invalid address: ").append(obj.getAddress()).append('\n');
        }

        if (obj.getPhone() == null || obj.getPhone().isEmpty()) {
            sb.append("     Invalid phone: ").append(obj.getPhone()).append('\n');
        }

        if (sb.length() > 0) {
            throw new InvalidCandidateException("\nInvalid candidate: \n" + sb.toString());
        }
    }
}
