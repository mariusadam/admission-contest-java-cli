package com.ubb.map.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@DatabaseTable(tableName = "option")
public class Option extends Entity {
    public static final String CANDIDATE_ID_FIELD_NAME = "candidate_id";
    public static final String DEPARTMENT_ID_FIELD_NAME = "department_id";

    @DatabaseField(foreign = true, columnName = CANDIDATE_ID_FIELD_NAME)
    @NotNull
    @Valid
    private Candidate candidate;

    @DatabaseField(foreign = true, columnName = DEPARTMENT_ID_FIELD_NAME)
    @NotNull
    @Valid
    private Department department;

    public Option() {
    }

    public Option(Candidate candidate, Department department) {
        this.candidate = candidate;
        this.department = department;
    }

    /**
     *
     * @param id         The unique id identifying an entity
     * @param candidate  The candidate
     * @param department The department
     */
    public Option(Integer id, Candidate candidate, Department department) {
        super(id);
        this.candidate = candidate;
        this.department = department;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public Department getDepartment() {
        return department;
    }

    public Option setCandidate(Candidate candidate) {
        this.candidate = candidate;
        return this;
    }

    public Option setDepartment(Department department) {
        this.department = department;
        return this;
    }

    @Override
    public String toCsvFormat(String separator) {
        return String.format(
                "%s%s%s%s%s",
                id.toString(),
                separator,
                candidate.getId().toString(),
                separator,
                department.getId().toString()
        );
    }
}
