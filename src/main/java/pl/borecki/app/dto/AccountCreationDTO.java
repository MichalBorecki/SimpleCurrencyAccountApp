package pl.borecki.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountCreationDTO {

    private String name;
    private String surname;
    private String pesel;
    private BigDecimal amountPln;

}