package helper.mapper;

import domain.Candidate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by marius on 11/20/16.
 */
public class CandidateMapper implements MapperInterface<Integer, Candidate> {
    @Override
    public Candidate createObject(ResultSet row) throws SQLException {
        Candidate candidate = new Candidate();
        candidate.setId(row.getInt("id"));
        candidate.setName(row.getString("name"));
        candidate.setAddress(row.getString("address"));
        candidate.setPhone(row.getString("phone"));

        return candidate;
    }

    @Override
    public Map<String, String> toMap(Candidate object) {
        Map<String, String> properties = new Hashtable<>();
        if (object.getId() != null) {
            properties.put("id", object.getId().toString());
        }
        properties.put("name", object.getName());
        properties.put("address", object.getAddress());
        properties.put("phone", object.getPhone());

        return properties;
    }
}
