package pl.borecki.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeQueryDTO {

    private String pesel;
    private BigDecimal amountToExchange;
    private String fromCurrency;
    private String toCurrency;

}