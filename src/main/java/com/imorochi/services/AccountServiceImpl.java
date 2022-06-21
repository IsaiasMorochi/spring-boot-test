package com.imorochi.services;

import com.imorochi.model.Account;
import com.imorochi.model.Bank;
import com.imorochi.repositories.AccountRepository;
import com.imorochi.repositories.BankRepository;

import java.math.BigDecimal;

public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public int reviewFullTransfer(Long bankId) {
        Bank bank = bankRepository.findById(bankId);
        return bank.getFullTransfer();
    }

    @Override
    public BigDecimal reviewSaldo(Long accountId) {
        Account account = accountRepository.findById(accountId);
        return account.getSaldo();
    }

    @Override
    public void transfer(Long bankId, Long originAccountNumber, Long destinationAccountNumber, BigDecimal amount) {
        Bank bank = bankRepository.findById(bankId);
        int fullTransfer = bank.getFullTransfer();
        bank.setFullTransfer(fullTransfer);
        bankRepository.update(bank);

        Account originAccount = accountRepository.findById(originAccountNumber);
        originAccount.debit(amount);
        accountRepository.update(originAccount);

        Account destinationAccount = accountRepository.findById(destinationAccountNumber);
        destinationAccount.credit(amount);
        accountRepository.update(destinationAccount);
    }
}
