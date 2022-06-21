package com.imorochi.mock;

import com.imorochi.model.Account;
import com.imorochi.model.Bank;

import java.math.BigDecimal;

public class Data {
//    public static final Account ACCOUNT_001 = new Account(1L, "Isaias", new BigDecimal("1000"));
//    public static final Account ACCOUNT_002 = new Account(2L, "Jhon", new BigDecimal("2000"));
//    public static final Bank BANK = new Bank(1L, "El banco financiero", 0);

    public static Account createdAccount001() {
        return new Account(1L, "Isaias", new BigDecimal("1000"));
    }

    public static Account createdAccount002() {
        return  new Account(2L, "Jhon", new BigDecimal("2000"));
    }

    public static Bank createdBank() {
        return new Bank(1L, "El banco financiero", 0);
    }

}
