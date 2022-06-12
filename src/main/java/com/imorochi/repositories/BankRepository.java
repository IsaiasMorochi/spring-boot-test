package com.imorochi.repositories;

import com.imorochi.model.Bank;

import java.util.List;

public interface BankRepository {
    List<Bank> findAll();
    Bank findById(Long id);
    void update(Bank bank);
}