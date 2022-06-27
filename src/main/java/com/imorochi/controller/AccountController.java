package com.imorochi.controller;

import com.imorochi.model.Account;
import com.imorochi.model.dto.TransferDto;
import com.imorochi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {

    @Autowired private AccountService accountService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Account> findById(@PathVariable(name = "id") Long id) {
        Account account = accountService.findById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferDto transferDto) {
        accountService.transfer(transferDto.getBankId(),
                transferDto.getOriginAccountNumber(),
                transferDto.getDestinationAccountNumber(),
                transferDto.getAmount());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("message", "Transaccion realizada con exito.");
        response.put("transactions", transferDto);

        return ResponseEntity.ok(response);
    }


}
