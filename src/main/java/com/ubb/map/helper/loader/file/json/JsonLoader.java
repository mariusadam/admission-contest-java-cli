package com.ubb.map.helper.loader.file.json;

import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.helper.loader.file.BaseFileLoader;
import org.json.JSONObject;
import com.ubb.map.validator.ValidatorInterface;

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
