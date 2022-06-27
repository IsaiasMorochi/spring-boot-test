package com.imorochi.controller;

import com.imorochi.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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

}