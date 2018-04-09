package com.capitalone.demo.service;

import java.util.List;

import com.capitalone.demo.controller.UnknownAccountException;
import com.capitalone.demo.entity.Account;

public interface AccountService {

  Account get(Integer accountNumber) throws UnknownAccountException;

  Account deposit(Integer accountNumber, int amount) throws UnknownAccountException;

  Account withdraw(Integer accountNumber, int amount) throws UnknownAccountException;

  void transfer(Integer fromAccountNumber, Integer toAccountNumber, int amount) throws UnknownAccountException;

  Account createAccount(Account account);

  void deleteAccount(Integer accountNumber) throws UnknownAccountException;

  List<Account> getAllAccounts();

  public Account updateAccountName(Integer accountNumber, String newName);
}
