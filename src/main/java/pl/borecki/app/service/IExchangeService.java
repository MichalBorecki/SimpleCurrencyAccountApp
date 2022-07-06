package pl.borecki.app.service;

import org.springframework.http.ResponseEntity;
import pl.borecki.app.dto.ExchangeQueryDTO;
import pl.borecki.app.dto.ExchangeResultDTO;

public interface IExchangeService {

    ResponseEntity<ExchangeResultDTO> exchange(ExchangeQueryDTO query);

}