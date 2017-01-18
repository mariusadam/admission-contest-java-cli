package com.ubb.map.domain;

/**
 * Created by marius on 11/6/16.
 */
public interface HasId<Id> {
    /**
     *
     * @return Id
     */
    Id getId();

    /**
     *
     * @param id The id of the object
     */
    HasId<Id> setId(Id id);
}
