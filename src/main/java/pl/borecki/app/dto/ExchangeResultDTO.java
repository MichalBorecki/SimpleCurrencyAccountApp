package pl.borecki.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExchangeResultDTO {

    private Boolean success;
    private BigDecimal amountToExchange;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal rate;
    private BigDecimal result;

}