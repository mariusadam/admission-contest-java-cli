package com.ubb.map.helper.loader.file.json;

import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.Option;
import org.json.JSONObject;
import com.ubb.map.repository.RepositoryInterface;
import com.ubb.map.validator.ValidatorInterface;

/**
 * @author Marius Adam
 */
public class OptionJsonLoader extends JsonLoader<Option> {
    private RepositoryInterface<Integer, Candidate>  cr;
    private RepositoryInterface<Integer, Department> dr;

    public OptionJsonLoader(ValidatorInterface<Option> validator, RepositoryInterface<Integer, Candidate> cr, RepositoryInterface<Integer, Department> dr) {
        super(validator);
        this.cr = cr;
        this.dr = dr;
    }

    @Override
    protected Option createFromJSONObject(JSONObject jsonObject) {
        return new Option(
                jsonObject.getInt("id"),
                this.cr.findById(jsonObject.getInt("candidate")),
                this.dr.findById(jsonObject.getInt("department"))
        );
    }
}
