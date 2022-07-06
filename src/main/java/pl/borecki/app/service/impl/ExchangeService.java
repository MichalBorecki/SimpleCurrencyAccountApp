package pl.borecki.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import pl.borecki.app.dto.ExchangeQueryDTO;
import pl.borecki.app.dto.ExchangeResultDTO;
import pl.borecki.app.dto.NbpRatesDTO;
import pl.borecki.app.entity.Account;
import pl.borecki.app.entity.Subaccount;
import pl.borecki.app.enums.CurrencyEnum;
import pl.borecki.app.exception.ExchangeProcessException;
import pl.borecki.app.service.IExchangeService;
import pl.borecki.app.validation.ExchangeQueryValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.Currency;

@Service
@Slf4j
public class ExchangeService implements IExchangeService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SubaccountService subaccountService;
    @Autowired
    private CurrencyExchangeRateService currencyExchangeRateService;

    @Override
    public ResponseEntity<ExchangeResultDTO> exchange(@RequestBody ExchangeQueryDTO query) {
        ExchangeQueryValidator.validate(query);

        Account account = getAccount(query);
        Subaccount subaccountForWithdrawal = getSubaccount(account.getId(), query.getFromCurrency());
        Subaccount subaccountForDeposit = getSubaccount(account.getId(), query.getToCurrency());

        NbpRatesDTO nbpRates;
        try {
            nbpRates = currencyExchangeRateService.getExchangeRates(Currency.getInstance(CurrencyEnum.USD.toString()));
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new ExchangeProcessException(e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        if(nbpRates == null || nbpRates.getAsk().isBlank() || nbpRates.getBid().isBlank()) {
            throw new ExchangeProcessException("No exchange rate found!");
        }

        BigDecimal rate = getRateFromNbpRates(query, nbpRates);

        if(!hasUserEnoughMoney(subaccountForWithdrawal.getBalance(), query.getAmountToExchange())) {
            throw new ExchangeProcessException(
                    "The amount on the subaccount is less than the specified amount to be exchanged! "
                            + "Actual balance is: " + subaccountForWithdrawal.getBalance()
                            + " " + subaccountForWithdrawal.getCurrencyCode());
        }

        withdrawalMoneyForExchange(query.getAmountToExchange(), subaccountForWithdrawal);
        BigDecimal exchangeResult = query.getAmountToExchange().multiply(rate).setScale(2, RoundingMode.HALF_UP);
        depositExchangedMoney(subaccountForDeposit, exchangeResult);

        return ResponseEntity.ok(new ExchangeResultDTO(
                true,
                query.getAmountToExchange(),
                query.getFromCurrency(),
                query.getToCurrency(),
                rate,
                exchangeResult));
    }

    private void withdrawalMoneyForExchange(BigDecimal amount, Subaccount subaccountForWithdrawal) {
        subaccountForWithdrawal.setBalance(subaccountForWithdrawal.getBalance().subtract(amount));
        subaccountService.save(subaccountForWithdrawal);
    }

    private void depositExchangedMoney(Subaccount subaccountForDeposit, BigDecimal exchangedAmount) {
        subaccountForDeposit.setBalance(subaccountForDeposit.getBalance().add(exchangedAmount));
        subaccountService.save(subaccountForDeposit);
    }

    private boolean hasUserEnoughMoney(BigDecimal balance, BigDecimal amountToExchange) {
        return balance.compareTo(amountToExchange) >= 0;
    }

    private Account getAccount(ExchangeQueryDTO exchangeDetails) {
        return accountService.getOneByPesel(exchangeDetails.getPesel());
    }

    private Subaccount getSubaccount(Long accountId, String currencyCode) {
        return subaccountService.getOneByAccountIdAndCurrencyCode(accountId, currencyCode);
    }

    private BigDecimal getRateFromNbpRates(ExchangeQueryDTO exchangeDetails, NbpRatesDTO nbpRates) {
        BigDecimal rate = null;
        if (exchangeDetails.getFromCurrency().equals(CurrencyEnum.PLN.name())) {
            rate = new BigDecimal(1).divide(new BigDecimal(nbpRates.getAsk()), 4, RoundingMode.HALF_UP);
        } else if (exchangeDetails.getFromCurrency().equals(CurrencyEnum.USD.name())) {
            rate = new BigDecimal(nbpRates.getBid());
        }
        if(rate == null) {
            throw new ExchangeProcessException("No exchange rate found!");
        }
        return rate;
    }

}

