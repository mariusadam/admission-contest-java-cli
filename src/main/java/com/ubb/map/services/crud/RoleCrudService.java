package com.ubb.map.services.crud;

import com.ubb.map.domain.HasId;
import com.ubb.map.domain.Operation;
import com.ubb.map.domain.Resource;
import com.ubb.map.domain.Role;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.repository.db.RoleRepository;
import com.ubb.map.validator.RoleValidator;
import com.ubb.map.validator.ValidatorInterface;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by marius on 1/21/2017.
 */
@Singleton
public class RoleCrudService extends BaseCrudService<Integer, Role> {
    private RoleRepository repository;

    @Inject
    public RoleCrudService(RoleRepository roleRepo, RoleValidator validator) {
        super(validator);
        repository = roleRepo;
    }

    public Role create(String name, String importance, Collection<Resource> resources) throws Exception{
        Role r = new Role();
        r.setName(name);
        r.setImportance(getIntOrNull(importance));
        r.setResources(resources);
        r.setAllowedOperations(Arrays.asList(Operation.values()));

        validator.validate(r);
        repository.insert(r);
        return r;
    }

    public Role update(Role r) throws Exception {
        return update(r.getId().toString(), r.getName(), r.getImportance().toString(), r.getResources());
    }

    public Role update(String id, String newName, String newImportance, Collection<Resource> resources) throws Exception {
        return update(id, newName, newImportance, resources, Arrays.asList(Operation.values()));
    }

    public Role update(String id, String newName, String newImportance, Collection<Resource> resources, Collection<Operation> ops) throws Exception{
        Role role = repository.findById(getIntOrNull(id));
        role.setName(newName);
        role.setImportance(getIntOrNull(newImportance));
        role.setResources(resources);
        role.setAllowedOperations(ops);

        validator.validate(role);
        repository.update(role);
        return role;
    }

    @Override
    protected RepositoryInterface<Integer, Role> getRepository() {
        return repository;
    }
}
