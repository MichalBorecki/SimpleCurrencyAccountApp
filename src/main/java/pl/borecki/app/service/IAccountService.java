package pl.borecki.app.service;

import pl.borecki.app.dto.AccountCreationDTO;
import pl.borecki.app.entity.Account;

import java.util.List;

public interface IAccountService {

    List<Account> getAll();

    Account getOneByPesel(String pesel);

    Account save(AccountCreationDTO accountCreation);

}

