package com.ubb.map.helper.loader.file.json;

import com.ubb.map.domain.Candidate;
import org.json.JSONObject;
import com.ubb.map.validator.ValidatorInterface;

/**
 * @author Marius Adam
 */
public class CandidateJsonLoader extends JsonLoader<Candidate> {
    public CandidateJsonLoader(ValidatorInterface<Candidate> validator) {
        super(validator);
    }

    @Override
    protected Candidate createFromJSONObject(JSONObject jsonObject) {
        return new Candidate(
                jsonObject.getInt("id"),
                jsonObject.getString("name"),
                jsonObject.getString("phone"),
                jsonObject.getString("address")
        );
    }
}
