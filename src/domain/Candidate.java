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

    @Override
    public Candidate clone() throws CloneNotSupportedException {
        Candidate cloned = (Candidate) super.clone();
        cloned.name      = this.name;
        cloned.phone     = this.phone;
        cloned.address   = this.address;

        return cloned;
    }
}
