package com.imorochi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imorochi.model.dto.TransferDto;
import com.imorochi.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static com.imorochi.mock.Data.createdAccount001;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void test_find_account() throws Exception {
        // Given
        when(accountService.findById(1L)).thenReturn(createdAccount001().orElseThrow());

        // When
        mockMvc.perform(get("/api/accounts/1").contentType(MediaType.APPLICATION_JSON))

        // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.person").value("Isaias"))
                .andExpect(jsonPath("$.saldo").value("1000"));

        verify(accountService).findById(1L);
    }

    @Test
    void test_transfer_amount() throws Exception {
        // Given
        TransferDto dtoBuilder = TransferDto.builder()
                .originAccountNumber(1L)
                .destinationAccountNumber(2L)
                .amount(new BigDecimal("100"))
                .bankId(1L)
                .build();
        // When
        mockMvc.perform(post("/api/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoBuilder)))
        // Then
        .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.message").value("Transaccion realizada con exito."))
                .andExpect(jsonPath("$.transactions.originAccountNumber").value(1L));
    }
}