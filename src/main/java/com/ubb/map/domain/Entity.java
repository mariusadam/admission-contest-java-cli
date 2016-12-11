package com.ubb.map.domain;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;

/**
 *
 */
public abstract class Entity extends TimestampableImpl implements HasId<Integer>, Serializable{
    @DatabaseField(generatedId = true)
    protected Integer id;

    public Entity() {
    }

    /**
     *
     * @param id The unique id identifying an entity
     */
    public Entity(Integer id) {
        this.id = id;
    }

    /**
     * @return Id
     */
    public Integer getId() {
        return id;
    }

    @Override
    public Entity setId(Integer id) {
        this.id = id;

        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Entity && this.id.equals(((Entity) obj).id);
    }

    public String toCsvFormat(String separator) {
        return null;
    }
}
