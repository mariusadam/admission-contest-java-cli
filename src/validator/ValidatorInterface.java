package validator;

import domain.Entity;
import exception.InvalidEntityException;

/**
 * Created by marius on 10/14/16.
 */
public interface ValidatorInterface {
    public void validate(Entity obj) throws InvalidEntityException;
}
