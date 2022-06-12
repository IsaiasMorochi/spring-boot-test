package com.imorochi.repositories;

import com.imorochi.model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();
    Account findById(Long id);
    void update(Account account);
}
