package com.imorochi.services;

import com.imorochi.model.Account;
import com.imorochi.model.Bank;
import com.imorochi.repositories.AccountRepository;
import com.imorochi.repositories.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    public AccountServiceImpl(AccountRepository accountRepository, BankRepository bankRepository) {
        this.accountRepository = accountRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public int reviewFullTransfer(Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow();
        return bank.getFullTransfer();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal reviewSaldo(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        return account.getSaldo();
    }

    @Override
    @Transactional
    public void transfer(Long bankId, Long originAccountNumber, Long destinationAccountNumber, BigDecimal amount) {

        Account originAccount = accountRepository.findById(originAccountNumber).orElseThrow();
        originAccount.debit(amount);
        accountRepository.save(originAccount);

        Account destinationAccount = accountRepository.findById(destinationAccountNumber).orElseThrow();
        destinationAccount.credit(amount);
        accountRepository.save(destinationAccount);

        Bank bank = bankRepository.findById(bankId).orElseThrow();
        int fullTransfer = bank.getFullTransfer();
        bank.setFullTransfer(++fullTransfer);
        bankRepository.save(bank);
    }

}
