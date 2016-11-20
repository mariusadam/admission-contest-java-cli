package helper.mapper;

import domain.HasId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by marius on 11/20/16.
 */
public interface MapperInterface<Id, T extends HasId<Id>> {
    T createObject(ResultSet row) throws SQLException;
    Map<String, String> toMap(T object);
}
