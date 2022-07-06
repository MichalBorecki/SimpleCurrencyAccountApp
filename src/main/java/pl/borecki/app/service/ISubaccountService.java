package pl.borecki.app.service;

import pl.borecki.app.entity.Subaccount;

public interface ISubaccountService {

    Subaccount save(Subaccount subaccount);

    Subaccount getOneByAccountIdAndCurrencyCode(Long accountId, String currencyCode);

}