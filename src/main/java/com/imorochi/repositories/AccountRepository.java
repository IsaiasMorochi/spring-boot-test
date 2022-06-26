package com.imorochi.repositories;

import com.imorochi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.person = ?1")
    Optional<Account> findByPerson(String person);
//    List<Account> findAll();
//    Optional<Account> findById(Long id);
//    void update(Account account);
}
