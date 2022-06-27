package com.imorochi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.imorochi.exception.InsufficientMoneyException;
import com.imorochi.model.Account;
import com.imorochi.model.Bank;
import com.imorochi.repositories.AccountRepository;
import com.imorochi.repositories.BankRepository;
import com.imorochi.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.imorochi.mock.Data.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

@SpringBootTest
class SpringBootUnitTest {

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    BankRepository bankRepository;

    //@InjectMocks //es necesario injectar la implementacion
    @Autowired
    AccountService accountService;

    @BeforeEach
    void setUp() {
//        accountRepository = mock(AccountRepository.class);
//        bankRepository = mock(BankRepository.class);

//        accountService = new AccountServiceImpl(accountRepository, bankRepository);

//        Data.ACCOUNT_001.setSaldo(new BigDecimal("1000"));
//        Data.ACCOUNT_002.setSaldo(new BigDecimal("2000"));
//        Data.BANK.setFullTransfer(0);
    }

    @Test
    void test_transfer_amount() {

        when(accountRepository.findById(1L)).thenReturn(createdAccount001());
        when(accountRepository.findById(2L)).thenReturn(createdAccount002());
        when(bankRepository.findById(1L)).thenReturn(createdBank());

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
        verify(accountRepository, times(2)).save(any(Account.class));

        verify(bankRepository, times(2)).findById(1L);
        verify(bankRepository, times(1)).save(any(Bank.class));

        verify(accountRepository, times(6)).findById(anyLong());
        verify(accountRepository, never()).findAll();

    }

    @Test
    void test_transfer_with_insufficient_balance() {

        when(accountRepository.findById(1L)).thenReturn(createdAccount001());
        when(accountRepository.findById(2L)).thenReturn(createdAccount002());
        when(bankRepository.findById(1L)).thenReturn(createdBank());

        BigDecimal originSaldo = accountService.reviewSaldo(1L);
        BigDecimal destinSaldo = accountService.reviewSaldo(2L);
        assertEquals("1000", originSaldo.toPlainString());
        assertEquals("2000", destinSaldo.toPlainString());

        assertThrows(InsufficientMoneyException.class, () -> {
            accountService.transfer(1L, 1L, 2L, new BigDecimal("1200"));
        });

        originSaldo = accountService.reviewSaldo(1L);
        destinSaldo = accountService.reviewSaldo(2L);

        assertEquals("1000", originSaldo.toPlainString());
        assertEquals("2000", destinSaldo.toPlainString());

        int total = accountService.reviewFullTransfer(1L);
        assertEquals(0, total);

        verify(accountRepository, times(3)).findById(1L);
        verify(accountRepository, times(2)).findById(2L);
        verify(accountRepository, never()).save(any(Account.class));

        verify(bankRepository, times(1)).findById(1L);
        verify(bankRepository, never()).save(any(Bank.class));

        verify(accountRepository, times(5)).findById(anyLong());
        verify(accountRepository, never()).findAll();
    }

    @Test
    void test_verify_same_object() {
        when(accountRepository.findById(1L)).thenReturn(createdAccount001());

        Account accountOne = accountService.findById(1L);
        Account accountTwo = accountService.findById(1L);

        assertSame(accountOne, accountTwo);
        assertTrue(accountOne == accountTwo);
        assertEquals("Isaias", accountOne.getPerson());
        assertEquals("Isaias", accountTwo.getPerson());

        verify(accountRepository, times(2)).findById(1L);
    }
}
