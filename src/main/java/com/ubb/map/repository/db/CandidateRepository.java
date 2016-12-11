package com.ubb.map.repository.db;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.HasId;

/**
 * Created by marius on 11.12.2016.
 */
public class CandidateRepository extends OrmRepository<Integer, Candidate> {
    public CandidateRepository(ConnectionSource connection, Class<Candidate> candidateClass) {
        super(connection, candidateClass);
    }
}
