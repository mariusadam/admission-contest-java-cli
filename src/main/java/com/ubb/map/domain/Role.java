package com.ubb.map.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by marius on 11.12.2016.
 */
@DatabaseTable(tableName = "role")
public class Role extends Entity {
    @DatabaseField
    @NotNull
    @Size(max = 255)
    private String name;

    @DatabaseField
    @NotNull(message = "Importance cannot be null")
    private Integer importance;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    @NotNull
    private ArrayList<Resource> resources;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    @NotNull
    private ArrayList<Operation> allowedOperations;

    public Role() {
        this.allowedOperations = new ArrayList<>();
        this.resources = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
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

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public Role setResources(Collection<Resource> resources) {
        this.resources.clear();
        this.resources.addAll(resources);
        return this;
    }

    public Collection<Operation> getAllowedOperations() {
        return allowedOperations;
    }

    public Role setAllowedOperations(Collection<Operation> allowedOperations) {
        this.allowedOperations.clear();
        this.allowedOperations.addAll(allowedOperations);
        return this;
    }

    public Role addAllowedOperation(Operation operation) {
        this.allowedOperations.add(operation);

        return this;
    }
}
