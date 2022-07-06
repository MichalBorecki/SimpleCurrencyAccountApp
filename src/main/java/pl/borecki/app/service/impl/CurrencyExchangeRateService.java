package pl.borecki.app.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.borecki.app.dto.NbpRatesDTO;
import pl.borecki.app.exception.ValidationException;
import pl.borecki.app.service.ICurrencyExchangeRateService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Currency;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
@Slf4j
public class CurrencyExchangeRateService implements ICurrencyExchangeRateService {

    @Override
    public NbpRatesDTO getExchangeRates(Currency currency) throws IOException, InterruptedException, URISyntaxException {
        if(currency == null || currency.getCurrencyCode() == null) {
           throw new ValidationException("Currency code has not been found!");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://api.nbp.pl/api/exchangerates/rates/c/" + currency.getCurrencyCode() + "/?format=json"))
                .GET()
                .timeout(Duration.of(10, SECONDS))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info(response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body());
        JsonNode rates = jsonNode.get("rates");
        if (rates.isArray()) {
            log.info(rates.get(0).toString());
            return objectMapper.readValue(rates.get(0).toString(), NbpRatesDTO.class);
        }
        return null;
    }

}

