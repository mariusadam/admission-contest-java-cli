package com.ubb.map.repository.db;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Candidate;
import com.ubb.map.repository.qualifiers.ConnectionSingleton;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CandidateRepository extends OrmRepository<Integer, Candidate> {

    @Inject
    public CandidateRepository(@ConnectionSingleton ConnectionSource connection) {
        super(connection, Candidate.class);
    }
}
