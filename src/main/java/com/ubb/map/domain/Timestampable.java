package com.ubb.map.domain;

import java.util.Date;

/**
 * Created by marius on 11.12.2016.
 */
public interface Timestampable {
    Timestampable setCreatedAt(Date date);

    Date getCreatedAt();

    Timestampable setUpdatedAt(Date date);

    Date getUpdatedAt();
}
