package com.ubb.map.repository.db;

import com.j256.ormlite.support.ConnectionSource;
import com.ubb.map.domain.Candidate;
import com.ubb.map.di.qualifiers.Connection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CandidateRepository extends OrmRepository<Integer, Candidate> {

    @Inject
    public CandidateRepository(@Connection ConnectionSource connection) {
        super(connection, Candidate.class);
    }
}
