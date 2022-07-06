package pl.borecki.app.validation;

import org.apache.commons.lang3.StringUtils;
import pl.borecki.app.dto.ExchangeQueryDTO;
import pl.borecki.app.enums.CurrencyEnum;
import pl.borecki.app.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

public class ExchangeQueryValidator {

    public static void validate(ExchangeQueryDTO query) {
        if(StringUtils.isBlank(query.getFromCurrency()) || StringUtils.isBlank(query.getToCurrency())) {
            throw new ValidationException("One of currency codes is incorrect!");
        }
        Set<String> currencyCodeSet = Set.of(query.getFromCurrency(), query.getToCurrency());
        if(!currencyCodeSet.containsAll(Arrays.asList(CurrencyEnum.PLN.toString(), CurrencyEnum.USD.toString()))) {
            throw new ValidationException("Currency code allowed: PLN, USD!");
        }
        if(StringUtils.isBlank(query.getPesel())) {
            throw new ValidationException("Pesel cannot be empty!");
        }
        if(query.getAmountToExchange() == null || query.getAmountToExchange().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("The amount to be exchage must be greater than zero!");
        }
    }

}
