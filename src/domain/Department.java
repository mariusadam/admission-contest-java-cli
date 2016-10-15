package domain;

/**
 *
 */
public class Department extends Entity{
    private String name;
    private Integer numberOfSeats;

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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param numberOfSeats {@link Integer}
     */
    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
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
    public String toCsvFormat() {
        return String.format("%s,%s,%s", id.toString(), name, numberOfSeats.toString());
    }
}