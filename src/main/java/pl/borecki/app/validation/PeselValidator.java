package pl.borecki.app.validation;

import org.hibernate.validator.internal.constraintvalidators.hv.pl.PESELValidator;
import pl.borecki.app.exception.ValidationException;

public class PeselValidator {

    public static void validate(String peselString) {
        CharSequence pesel = peselString;
        PESELValidator validator = new PESELValidator();
        validator.initialize(null);
        if(!validator.isValid(pesel, null)) {
            throw new ValidationException("PESEL is not valid!");
        }
    }
}
