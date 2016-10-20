package helper.loader.file.json;

import exception.InvalidObjectException;
import helper.loader.file.BaseFileLoader;
import org.json.JSONObject;
import validator.ValidatorInterface;

import java.util.Map;

/**
 * @author Marius Adam
 */
public abstract class JsonLoader<T> extends BaseFileLoader<T> {
    public JsonLoader(ValidatorInterface<T> validator) {
        super(validator);
    }

    @Override
    protected T createFromFormat(String parts) throws InvalidObjectException {
        return this.createFromJSONObject(new JSONObject(parts));
    }

    protected abstract T createFromJSONObject(JSONObject jsonObject);
}
