package com.example.FinalProject.service;

import com.example.FinalProject.model.Account;
import com.example.FinalProject.model.Transaction;
import com.example.FinalProject.repository.AccountRepository;
import com.example.FinalProject.repository.TransactionRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    AccountService accountService;
    @Mock
    AccountRepository accountRepository;

    @SneakyThrows
    @Test
    public void getAllTransactions_ResponseSuccessfully(){
        Account account = new Account(1L, "LHV", BigDecimal.TEN, "EUR",
                BigDecimal.ZERO);
        Transaction transaction1 = new Transaction(BigDecimal.valueOf(100), "DEPOSIT", account, LocalDate.now());
        Transaction transaction2 = new Transaction(BigDecimal.valueOf(200), "DEPOSIT", account, LocalDate.now());
        when(transactionRepository.findAll()).thenReturn(List.of(transaction2, transaction1));
        TransactionService transactionService = new TransactionService(transactionRepository);

        List<Transaction> transactions = transactionService.getFullTransactionList();

        assert transactions.size() > 0;
        assert transactions.size() == 2;
        assertEquals(transactions.get(0).getAmount(), BigDecimal.valueOf(200));
        assertEquals(transactions.get(1).getAccount().getAccountName(), "LHV");
    }

    @Test
    public void saveTransactions_ResponseSuccessfully() {
        Account account = new Account(1L, "LHV", BigDecimal.valueOf(10), "EUR",
                BigDecimal.ZERO);
        Transaction transaction = new Transaction(1L, BigDecimal.valueOf(100), "DEPOSIT", account, LocalDate.now());

        TransactionService transactionService = new TransactionService(transactionRepository);

        when(transactionRepository.save(transaction)).thenReturn(transaction);


        Transaction testTransaction = transactionService.save(transaction);


        assertEquals(testTransaction.getAmount(), BigDecimal.valueOf(100));
        assertEquals(testTransaction.getAccount().getAccountName(), "LHV");
        assertEquals(testTransaction.getAccount().getBalance(), BigDecimal.valueOf(10));
    }

}
