package com.imorochi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.imorochi.model.Account;
import com.imorochi.model.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.imorochi.mock.Data;
import com.imorochi.repositories.AccountRepository;
import com.imorochi.repositories.BankRepository;
import com.imorochi.services.AccountService;
import com.imorochi.services.AccountServiceImpl;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class SpringBootTestApplicationTests {

    AccountRepository accountRepository;
    BankRepository bankRepository;

    AccountService accountService;

    @BeforeEach
    void setU() {
        accountRepository = mock(AccountRepository.class);
        bankRepository = mock(BankRepository.class);

        accountService = new AccountServiceImpl(accountRepository, bankRepository);
    }

    @Test
    void test_transfer_amount() {

        when(accountRepository.findById(1L)).thenReturn(Data.ACCOUNT_001);
        when(accountRepository.findById(2L)).thenReturn(Data.ACCOUNT_002);
        when(bankRepository.findById(1L)).thenReturn(Data.BANK);

        BigDecimal originSaldo = accountService.reviewSaldo(1L);
        BigDecimal destinSaldo = accountService.reviewSaldo(2L);
        assertEquals("1000", originSaldo.toPlainString());
        assertEquals("2000", destinSaldo.toPlainString());

        accountService.transfer(1L, 1L, 2L, new BigDecimal("100"));

        originSaldo = accountService.reviewSaldo(1L);
        destinSaldo = accountService.reviewSaldo(2L);

        assertEquals("900", originSaldo.toPlainString());
        assertEquals("2100", destinSaldo.toPlainString());

        int total = accountService.reviewFullTransfer(1L);
        assertEquals(1, total);

        verify(accountRepository, times(3)).findById(1L);
        verify(accountRepository, times(3)).findById(2L);
        verify(accountRepository, times(2)).update(any(Account.class));

        verify(bankRepository, times(2)).findById(1L);
        verify(bankRepository, times(1)).update(any(Bank.class));

    }

}
