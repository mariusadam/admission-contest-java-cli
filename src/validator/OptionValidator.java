package validator;

import domain.Entity;
import domain.Option;
import exception.InvalidDepartmentException;
import exception.InvalidObjectException;

/**
 * Created by marius on 10/16/16.
 */
public class OptionValidator implements ValidatorInterface<Option> {
    @Override
    public void validate(Option obj) throws InvalidObjectException {
        if (obj == null) {
            throw new InvalidObjectException("Invalid Department object.");
        }

        StringBuilder sb = new StringBuilder();

        if (obj.getId() < 0) {
            sb.append("     Invalid id: ").append(obj.getId()).append('\n');
        }

        if (sb.length() > 0) {
            throw new InvalidDepartmentException("\nInvalid option: \n" + sb.toString());
        }
    }
}
