package com.ubb.map.services;

import com.ubb.map.domain.*;
import com.ubb.map.repository.db.UserRoleRepository;
import com.ubb.map.repository.qualifiers.UserRoleRepo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by marius on 11.12.2016.
 */
@Singleton
public class AclService {
    private UserRoleRepository userRoleRepo;

    @Inject
    public AclService(UserRoleRepository userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }

    public AclService setUserRoleRepo(UserRoleRepository userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
        return this;
    }

    public UserRoleRepository getUserRoleRepo() {
        return userRoleRepo;
    }

    public Boolean isAllowed(User user, Resource resource, Operation operation) {
        switch (resource) {
            case CANDIDATE:
            case DEPARTMENT:
            case OPTION:
                return true;
            case ROLE:
            case USER:
                return false;
        }
        for (Role role : this.userRoleRepo.getRoles(user)) {
            if (role.getResource().equals(resource) && role.getAllowedOperations().contains(operation)) {
                return true;
            }
        }

        return false;
    }

    public Boolean isAllowed(User user, Resource resource) {
        for (Operation operation : Operation.values()) {
            if (!this.isAllowed(user, resource, operation)) {
                return false;
            }
        }

        return true;
    }
}
