package com.ubb.map.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@DatabaseTable(tableName = "department")
public class Department extends Entity {
    @DatabaseField
    @NotNull(message = "Code may not be null")
    @Size(min = 3, max = 20, message = "Code must have between {min} and {max} characters")
    private String code;

    @DatabaseField
    @NotNull(message = "Name may not be null")
    @Size(min = 3, max = 50, message = "Name must have between {min} and {max} characters")
    private String name;

    @DatabaseField(columnName = "number_of_seats")
    @NotNull(message = "Number of seats cannot be null")
    @Max(value = 999, message = "Number of seats cannot be greater than {value}")
    @Min(value = 1, message = "Number of seats cannot be lower than {value}")
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