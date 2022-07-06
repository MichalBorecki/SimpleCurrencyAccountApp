package pl.borecki.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NbpRatesDTO {

    private String no;
    private String effectiveDate;
    private String bid;
    private String ask;

}