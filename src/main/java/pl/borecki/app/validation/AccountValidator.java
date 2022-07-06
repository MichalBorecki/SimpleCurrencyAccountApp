package pl.borecki.app.validation;

import pl.borecki.app.constants.AppContstans;
import pl.borecki.app.dto.AccountCreationDTO;
import pl.borecki.app.exception.ValidationException;
import pl.borecki.app.utils.PeselUtils;

import java.math.BigDecimal;

public class AccountValidator {

    public static void validate(AccountCreationDTO accountCreation) {
        if(!PeselUtils.hasUserMinimalAge(accountCreation.getPesel())) {
            throw new ValidationException("User must be " + AppContstans.ACCOUNT_OWNER_MINIMAL_AGE + " years of age!");
        }
        if(!isValidAmountPln(accountCreation.getAmountPln())) {
            throw new ValidationException("The amount must be greater than zero!");
        }
    }

    private static boolean isValidAmountPln(BigDecimal amountPln) {
        return amountPln.compareTo(BigDecimal.ZERO) > -1;
    }
}
