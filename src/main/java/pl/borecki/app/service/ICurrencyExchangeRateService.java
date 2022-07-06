package pl.borecki.app.service;

import pl.borecki.app.dto.NbpRatesDTO;

import java.util.Currency;

public interface ICurrencyExchangeRateService {

    NbpRatesDTO getExchangeRates(Currency currency) throws Exception;

}

