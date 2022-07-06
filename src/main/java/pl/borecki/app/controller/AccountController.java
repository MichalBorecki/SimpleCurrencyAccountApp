package pl.borecki.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.borecki.app.dto.AccountCreationDTO;
import pl.borecki.app.entity.Account;
import pl.borecki.app.exception.RecordNotFoundException;
import pl.borecki.app.service.IAccountService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping
    List<Account> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/{pesel}")
    Account getOneByPesel(@PathVariable String pesel) {
        return accountService.getOneByPesel(pesel);
    }

    @PostMapping
    ResponseEntity<Account> save(@RequestBody AccountCreationDTO accountCreation) {
        Account account = accountService.save(accountCreation);
        return ResponseEntity.created(URI.create("/api/accounts/" + account.getPesel())).body(account);
    }

}

