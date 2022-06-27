package com.imorochi.controller;

import com.imorochi.model.Account;
import com.imorochi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    @Autowired private AccountService accountService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> findById(@PathVariable(name = "id") Long id) {
        Account account = accountService.findById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


}
