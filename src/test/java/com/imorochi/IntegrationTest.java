package com.imorochi;

import com.imorochi.model.Account;
import com.imorochi.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegrationTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    void test_find_account() {
        Optional<Account> account = accountRepository.findById(1L);
        assertTrue(account.isPresent());
        assertEquals("Isaias", account.orElseThrow().getPerson());
    }

    @Test
    void test_find_by_person() {
        Optional<Account> account = accountRepository.findByPerson("Isaias");
        assertTrue(account.isPresent());
        assertEquals("Isaias", account.orElseThrow().getPerson());
        assertEquals("1000.00", account.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void test_find_by_person_not_present() {
        Optional<Account> account = accountRepository.findByPerson("IsaiasV2");
        assertThrows(NoSuchElementException.class, account::orElseThrow);
        assertFalse(account.isPresent());
    }

    @Test
    void test_find_all_accounts() {
        List<Account> accounts = accountRepository.findAll();
        assertFalse(accounts.isEmpty());
        assertEquals(2, accounts.size());
    }

    @Test
    void test_save() {
        Account account = new Account(null, "Pepe", new BigDecimal("3000"));

        Account newAccount = accountRepository.save(account);
        //Account newAccount = accountRepository.findByPerson("Pepe").orElseThrow();
        //Account newAccount = accountRepository.findById(save.getId()).orElseThrow();

        assertEquals(3L, newAccount.getId());
        assertEquals("Pepe", newAccount.getPerson());
        assertEquals("3000", newAccount.getSaldo().toPlainString());
    }

    @Test
    void test_update() {
        Account account = new Account(null, "Pepe", new BigDecimal("3000"));

        Account newAccount = accountRepository.save(account);
        //accountRepository.findByPerson("Pepe").orElseThrow();

        //assertEquals(3L, newAccount.getId());
        assertEquals("Pepe", newAccount.getPerson());
        assertEquals("3000", newAccount.getSaldo().toPlainString());


        newAccount.setSaldo(new BigDecimal("3800"));
        Account accountUpdate = accountRepository.save(newAccount);

        assertEquals("Pepe", accountUpdate.getPerson());
        assertEquals("3800", accountUpdate.getSaldo().toPlainString());
    }

    @Test
    void test_delete_account() {
        Account account = accountRepository.findById(2L).orElseThrow();
        assertEquals("Jhon", account.getPerson());

        accountRepository.delete(account);

        assertThrows(NoSuchElementException.class, () -> accountRepository.findById(2L).orElseThrow());

        assertEquals(1, accountRepository.findAll().size());
    }

}
