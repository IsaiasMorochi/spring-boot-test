package com.imorochi.services;

import com.imorochi.model.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account findById(Long id);
    int reviewFullTransfer(Long bankId);
    BigDecimal reviewSaldo(Long accountId);
    void transfer(Long originAccountNumber, Long destinationAccountNumber, BigDecimal amount);

}
