package com.example.FinalProject.service;

import com.example.FinalProject.model.*;
import com.example.FinalProject.repository.AccountRepository;
import com.example.FinalProject.repository.CurrencyRateRepository;
import com.example.FinalProject.repository.StockRepository;
import com.example.FinalProject.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account save(Account account) {

        Account savedAccount = toAccount(account);
        return accountRepository.save(savedAccount);
    }

    private Account toAccount(Account account) {
        return Account.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .accountName(account.getAccountName())
                .currency(account.getCurrency())
                .build();
    }


    public Account updateAccountBalanceByTransactionType(Long id, Transaction transaction) {

        Account account = accountRepository.findById(id).orElse(null);
        BigDecimal total;
        if (transaction.getTransactionType().equalsIgnoreCase("DEPOSIT")) {
            total = account.getBalance().add(transaction.getAmount());
            account.setBalance(total);
            transaction.setTransactionType("DEPOSIT");
        } else {
            if (transaction.getAmount().compareTo(account.getBalance()) > 0) {
                throw new IllegalStateException("Withdrawal amount exceeds account balance");
            }
            total = account.getBalance().subtract(transaction.getAmount());
            account.setBalance(total);
            transaction.setTransactionType("WITHDRAW");
        }
        transaction.setAmount(transaction.getAmount());
        transaction.setTransactionDate(transaction.getTransactionDate());
        transaction.setAccount(account);//
        account.setId(id);
        transactionRepository.save(transaction);
        return account;
    }
}
