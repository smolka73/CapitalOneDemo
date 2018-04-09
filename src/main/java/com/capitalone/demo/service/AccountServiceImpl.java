package com.capitalone.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capitalone.demo.controller.UnknownAccountException;
import com.capitalone.demo.entity.Account;
import com.capitalone.demo.repository.AccountRepository;

@Transactional
@Service
class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Transactional(readOnly = true)
  @Override
  public Account get(Integer accountNumber) {
    return getAccountEntity(accountNumber);
  }

  @Override
  public Account deposit(Integer accountNumber, int amount) {
    Account account = getAccountEntity(accountNumber);
    account.deposit(amount);
    return accountRepository.save(account);
  }

  @Override
  public Account withdraw(Integer accountNumber, int amount) {
    Account account = getAccountEntity(accountNumber);
    account.withdraw(amount);
    return accountRepository.save(account);
  }

  @Override
  public void transfer(Integer fromAccountNumber, Integer toAccountNumber, int amount) {
    Account fromAccount = getAccountEntity(fromAccountNumber);
    Account toAccount = getAccountEntity(toAccountNumber);
    fromAccount.withdraw(amount);
    toAccount.deposit(amount);
    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);
  }

  @Override
  public Account createAccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Account updateAccountName(Integer accountNumber, String newName) {
    Account account = getAccountEntity(accountNumber);
    account.setFullName(newName);

    return accountRepository.save(account);
  }

  @Override
  public void deleteAccount(Integer accountNumber) throws UnknownAccountException {
    try {
      accountRepository.deleteById(accountNumber);
    } catch (EmptyResultDataAccessException e) {
      throw new UnknownAccountException(accountNumber, e);
    }
  }

  @Transactional(readOnly = true)
  @Override
  public List<Account> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();

    return accounts;
  }

  private Account getAccountEntity(Integer accountNumber) throws UnknownAccountException {
    Optional<Account> account = accountRepository.findById(accountNumber);
    if (account == null || !account.isPresent()) {
      throw new UnknownAccountException(accountNumber);
    }
    return account.get();
  }
}
