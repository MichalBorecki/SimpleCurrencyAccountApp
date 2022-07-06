package pl.borecki.app.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.borecki.app.dto.AccountCreationDTO;
import pl.borecki.app.entity.Account;
import pl.borecki.app.entity.Subaccount;
import pl.borecki.app.enums.CurrencyEnum;
import pl.borecki.app.exception.RecordNotFoundException;
import pl.borecki.app.exception.ValidationException;
import pl.borecki.app.repository.AccountRepository;
import pl.borecki.app.service.IAccountService;
import pl.borecki.app.validation.AccountValidator;
import pl.borecki.app.validation.PeselValidator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getOneByPesel(String pesel) {
        return accountRepository.findOneByPesel(pesel)
                .orElseThrow(() -> new RecordNotFoundException("Account for PESEL: " + pesel + " has not been found!"));
    }

    @Override
    public Account save(AccountCreationDTO accountCreation) {
        PeselValidator.validate(accountCreation.getPesel());
        AccountValidator.validate(accountCreation);

        if(hasUserAccount(accountCreation.getPesel())) {
            throw new ValidationException("A user with PESEL: " + accountCreation.getPesel() + " already has an account!");
        }

        Account account = new Account();

        List<Subaccount> subaccountList = Arrays.asList(
                new Subaccount(account, CurrencyEnum.PLN.toString(), accountCreation.getAmountPln()),
                new Subaccount(account, CurrencyEnum.USD.toString(), BigDecimal.ZERO));

        account.setName(accountCreation.getName());
        account.setSurname(accountCreation.getSurname());
        account.setPesel(accountCreation.getPesel());
        account.setSubaccountList(subaccountList);
        return accountRepository.save(account);
    }

    private boolean hasUserAccount(String pesel) {
        return accountRepository.findOneByPesel(pesel).isPresent();
    }
}

