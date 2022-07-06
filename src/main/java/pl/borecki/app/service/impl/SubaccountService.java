package pl.borecki.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borecki.app.entity.Subaccount;
import pl.borecki.app.exception.RecordNotFoundException;
import pl.borecki.app.repository.SubaccountRepository;
import pl.borecki.app.service.ISubaccountService;

@Service
public class SubaccountService implements ISubaccountService {

    @Autowired
    private SubaccountRepository subaccountRepository;

    @Override
    public Subaccount save(Subaccount subaccount) {
        return subaccountRepository.save(subaccount);
    }

    @Override
    public Subaccount getOneByAccountIdAndCurrencyCode(Long accountId, String currencyCode) {
        return subaccountRepository.findOneByAccountIdAndCurrencyCode(accountId, currencyCode)
                .orElseThrow(() -> new RecordNotFoundException(
                        "Subaccount for " + currencyCode + " and accountID: " + accountId + " has not been found!"));
    }

}