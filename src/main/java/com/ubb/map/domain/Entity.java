package com.ubb.map.domain;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;

import javax.validation.constraints.NotNull;

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
        if (!(obj instanceof Entity)) {
            return false;
        }

        if (id == null && ((Entity) obj).id == null) {
            return true;
        }

        if (id == null || ((Entity) obj).id == null) {
            return false;
        }

        return id.equals(((Entity) obj).id);
    }

    public String toCsvFormat(String separator) {
        return null;
    }
}
