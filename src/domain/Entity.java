package domain;

/**
 *
 */
public abstract class Entity implements Cloneable{
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

    public abstract String toCsvFormat();

    @Override
    public Entity clone() throws CloneNotSupportedException {
        Entity cloned = (Entity) super.clone();
        cloned.id = this.id;

        return cloned;
    }
}
