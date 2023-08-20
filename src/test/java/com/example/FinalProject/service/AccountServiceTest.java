package com.example.FinalProject.service;

import com.example.FinalProject.model.Account;
import com.example.FinalProject.model.Transaction;
import com.example.FinalProject.repository.AccountRepository;
import com.example.FinalProject.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    TransactionService transactionService;
    @InjectMocks
    AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateAccountBalanceByTransactionType_responseSuccessfully() {
        Account account = new Account(1L, "LHV", BigDecimal.valueOf(10), "EUR",
                BigDecimal.ZERO);
        Transaction transaction = new Transaction(1L, BigDecimal.valueOf(100), "DEPOSIT", account, LocalDate.now());
        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));

        Account updatedAccount = accountService.updateAccountBalanceByTransactionType(account.getId(), transaction);

        verify(transactionRepository, times(1)).save(transaction);

        // Assertions
        assertThat(updatedAccount.getId()).isEqualTo(account.getId());
        assertThat(updatedAccount.getBalance()).isEqualTo(BigDecimal.valueOf(10).add(BigDecimal.valueOf(100)));
        assertEquals(updatedAccount.getBalance(), BigDecimal.valueOf(110));


    }
}



