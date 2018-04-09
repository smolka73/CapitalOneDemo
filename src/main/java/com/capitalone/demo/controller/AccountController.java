package com.capitalone.demo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.capitalone.demo.entity.Account;
import com.capitalone.demo.service.AccountService;

@RestController
@RequestMapping("accounts")
public class AccountController {

  private final AccountService accountService;

  @Autowired
  AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  /*-
  @Autowired
  private AccountRepository accountRepository;
  -*/
  @GetMapping
  public List<Account> getAllAccounts() {
    // return this.accountRepository.findAll();
    return accountService.getAllAccounts();
  }

  @GetMapping("/{accountNumber}")
  public Account getAccount(@PathVariable("accountNumber") final Integer account) {
    return accountService.get(account); // account;
  }

  @PostMapping
  public ResponseEntity<Account> addAccount(@RequestBody final Account account) {
    // final Account newAccount = this.accountRepository.save(account);

    final Account newAccount = accountService.createAccount(account);
    final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{accountNumber}").buildAndExpand(newAccount.getAccountNumber()).toUri();

    return ResponseEntity.created(location).build();
  }

  @PostMapping("/{accountNumber}/deposit")
  void deposit(@PathVariable("accountNumber") int accountNumber, @Valid @RequestBody Amount amount) {
    accountService.deposit(accountNumber, amount.getAmount());
  }

  @PostMapping("/{accountNumber}/withdraw")
  void withdraw(@PathVariable("accountNumber") int accountNumber, @Valid @RequestBody Amount amount) {
    accountService.withdraw(accountNumber, amount.getAmount());
  }

  @PutMapping("/{accountNumber}")
  public Account updateAccount(@PathVariable("accountNumber") final Integer accountNumber, @RequestBody final Account account) {
    return accountService.updateAccount(accountNumber, account);
  }

  @DeleteMapping("/{accountNumber}")
  public void deleteAccount(@PathVariable("accountNumber") final Integer accountNumber) {
    accountService.deleteAccount(accountNumber);
  }

  @ExceptionHandler(TransactionSystemException.class)
  @ResponseStatus(HttpStatus.CONFLICT) // 409
  void constraintViolation() {}

  @ExceptionHandler(UnknownAccountException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND) // 404
  void notFound() {}

}