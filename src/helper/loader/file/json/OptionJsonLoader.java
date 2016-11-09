package helper.loader.file.json;

import domain.Candidate;
import domain.Department;
import domain.Option;
import org.json.JSONObject;
import repository.RepositoryInterface;
import validator.ValidatorInterface;

/**
 * @author Marius Adam
 */
public class OptionJsonLoader extends JsonLoader<Option> {
    private RepositoryInterface<Integer, Candidate>  cr;
    private RepositoryInterface<String, Department> dr;

    public OptionJsonLoader(ValidatorInterface<Option> validator, RepositoryInterface<Integer, Candidate> cr, RepositoryInterface<String, Department> dr) {
        super(validator);
        this.cr = cr;
        this.dr = dr;
    }

    @Override
    protected Option createFromJSONObject(JSONObject jsonObject) {
        return new Option(
                jsonObject.getInt("id"),
                this.cr.findById(jsonObject.getInt("candidate")),
                this.dr.findById(jsonObject.getString("department"))
        );
    }
}
