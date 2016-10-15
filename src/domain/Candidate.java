package domain;

/**
 * Created by marius on 10/8/16.
 */
public class Candidate extends Entity {
    private String name;
    private String phone;
    private String address;

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

    /**
     * @return {@link String}
     */
    public String getName() {
        return name;
    }

    /**
     * @param name {@link String}
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * @param phone {@link String}
     */
    public void setPhone(String phone) {
        this.phone = phone;
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
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return {@link String}
     */
    @Override
    public String toString() {
        return "Candidate " + this.name + " with id " + this.id;
    }

    @Override
    public String toCsvFormat() {
        return String.format("%s,%s,%s,%s", id.toString(), name, phone, address);
    }
}
