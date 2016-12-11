package com.ubb.map.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "department")
public class Department extends Entity {
    @DatabaseField
    private String code;

    @DatabaseField
    private String name;

    @DatabaseField(columnName = "number_of_seats")
    private Integer numberOfSeats;

    public Department() {
    }

    public Department(String name, Integer numberOfSeats) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * @param id            {@link Integer}
     * @param name          {@link String}
     * @param numberOfSeats {@link Integer}
     */
    public Department(Integer id, String name, Integer numberOfSeats) {
        super(id);
        this.name = name;
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * @param name {@link String}
     */
    public Department setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @param numberOfSeats {@link Integer}
     */
    public Department setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        return this;
    }

    /**
     * @return {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * @return {@link Integer}
     */
    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * @return {@link String}
     */
    @Override
    public String toString() {
        return "Department " + this.name + " with id " + this.id;
    }

    @Override
    public String toCsvFormat(String separator) {
        return String.format(
                "%s%s%s%s%s",
                id,
                separator,
                name,
                separator,
                numberOfSeats.toString()
        );
    }

    public String getCode() {
        return code;
    }

    public Department setCode(String code) {
        this.code = code;
        return this;
    }
}