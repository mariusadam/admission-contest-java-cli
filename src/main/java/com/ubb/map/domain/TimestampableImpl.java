package com.ubb.map.domain;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created by marius on 11.12.2016.
 */
public abstract class TimestampableImpl implements Timestampable {
    @DatabaseField(columnName = "created_at")
    private Date createdAt;

    @DatabaseField(columnName = "updated_at")
    private Date updatedAt;
    
    @Override
    public Timestampable setCreatedAt(Date date) {
        this.createdAt = date;
        return this;
    }

    @Override
    public Date getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public Timestampable setUpdatedAt(Date date) {
        this.updatedAt = date;
        return this;
    }

    @Override
    public Date getUpdatedAt() {
        return this.updatedAt;
    }
}
