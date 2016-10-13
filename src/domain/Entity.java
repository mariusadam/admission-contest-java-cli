package domain;

/**
 *
 */
public class Entity {
    protected Integer id;

    /**
     *
     * @param id The unique id identifying an entity
     */
    public Entity(Integer id) {
        this.id = id;
    }

    /**
     * @return {@link Integer}
     */
    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Entity && this.id.equals(((Entity) obj).id);
    }
}
