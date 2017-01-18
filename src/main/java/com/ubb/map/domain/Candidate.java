package com.ubb.map.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@DatabaseTable(tableName = "candidate")
public class Candidate extends Entity {
    @DatabaseField
    @NotNull(message = "Name may not be null")
    @Size(min = 5, max = 50, message = "Name must be between {min} and {max} characters")
    private String name;

    @DatabaseField
    @NotNull(message = "Phone may not be null")
    @Size(min = 10, max = 10, message = "Phone must have {max} characters")
    private String phone;

    @DatabaseField
    @NotNull(message = "Address may not be null")
    @Size(min = 1, max = 255, message = "Address must have between {min} and {max} characters")
    private String address;

    public Candidate() {
    }

    public Candidate(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    /**
     * @param id      {@link Integer}
     * @param name    {@link String}
     * @param phone   {@link String}
     * @param address {@link String}
     */
    public Candidate(Integer id, String name, String phone, String address) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    protected Candidate(Candidate other) {
        super(other.getId());
        this.name = other.getName();
        this.phone = other.getPhone();
        this.address = other.getAddress();
    }

    /**
     * @return {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * @param name {@link String}
     */
    public Candidate setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * @param phone {@link String}
     */
    public Candidate setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * @return {@link String}
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address {@link String}
     */
    public Candidate setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * @return {@link String}
     */
    @Override
    public String toString() {
        return "Candidate " + this.name + " with id " + this.id;
    }

    @Override
    public String toCsvFormat(String separator) {
        return String.format(
                "%s%s%s%s%s%s%s",
                id.toString(),
                separator,
                name,
                separator,
                phone,
                separator,
                address
        );
    }
}
