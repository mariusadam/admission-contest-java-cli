package helper.loader.file.json;

import domain.Candidate;
import org.json.JSONObject;
import validator.ValidatorInterface;

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
