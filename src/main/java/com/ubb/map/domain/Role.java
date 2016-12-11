package com.ubb.map.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by marius on 11.12.2016.
 */
public class Role extends Entity{
    @DatabaseField
    private String                name;

    @DatabaseField
    private Integer               importance;

    @DatabaseField
    private Resource              resource;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<Operation> allowedOperations;

    public Role() {
        this.allowedOperations = new ArrayList<>();
    }

    public Role(Integer id, String name, Integer importance) {
        super(id);
        this.name = name;
        this.importance = importance;
    }

    public String getName() {
        return name;
    }

    public Integer getImportance() {
        return importance;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public Role setImportance(Integer importance) {
        this.importance = importance;
        return this;
    }

    @Override
    public String toCsvFormat(String separator) {
        return null;
    }

    public Resource getResource() {
        return resource;
    }

    public Role setResource(Resource resource) {
        this.resource = resource;
        return this;
    }

    public Collection<Operation> getAllowedOperations() {
        return allowedOperations;
    }

    public Role setAllowedOperations(ArrayList<Operation> allowedOperations) {
        this.allowedOperations = allowedOperations;
        return this;
    }

    public Role addAllowedOperation(Operation operation) {
        this.allowedOperations.add(operation);

        return this;
    }
}
