package com.ubb.map.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "candidate")
public class Candidate extends Entity{
    @DatabaseField
    private String name;

    @DatabaseField
    private String phone;

    @DatabaseField
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
