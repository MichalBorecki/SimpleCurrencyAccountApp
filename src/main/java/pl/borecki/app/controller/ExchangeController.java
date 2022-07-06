package pl.borecki.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.borecki.app.dto.ExchangeQueryDTO;
import pl.borecki.app.dto.ExchangeResultDTO;
import pl.borecki.app.service.IExchangeService;

@RestController
public class ExchangeController {

    @Autowired
    private IExchangeService exchangeService;

    @PostMapping("/api/exchange")
    ResponseEntity<ExchangeResultDTO> exchange(@RequestBody ExchangeQueryDTO exchangeDetails) {
        return exchangeService.exchange(exchangeDetails);
    }

}