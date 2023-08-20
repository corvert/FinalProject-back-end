package com.example.FinalProject.controller;

import com.example.FinalProject.model.*;
import com.example.FinalProject.service.AccountService;
import com.example.FinalProject.service.StockService;
import com.example.FinalProject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private StockService stockService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id) {
        Account account = accountService.findAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{id}/transaction-list")
    public ResponseEntity<List<Transaction>> getTransactionListByAccountId(@PathVariable("id") Long id) {
        List<Transaction> transactionsOfAccount = transactionService.getTransactionsListByAccountId(id);
        return ResponseEntity.ok(transactionsOfAccount);
    }

    @GetMapping("/{id}/stock-list")
    public ResponseEntity<List<Stock>> getStockListByAccountId(@PathVariable("id") Long id) {
        List<Stock> stocksOfAccount = stockService.getStocksListByAccountId(id);
        return ResponseEntity.ok(stocksOfAccount);
    }

    @PostMapping("/{id}/create-stock")
    public ResponseEntity<Stock> createStock(@PathVariable("id") Long accountId, @RequestBody Stock stock) {
        Account account = accountService.findAccountById(accountId);
        Stock savedStock = stockService.save(account, stock);
        return ResponseEntity.ok(savedStock);
    }

    @PostMapping("/add-account")
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        accountService.save(account);
        return ResponseEntity.ok(account);
    }




    @PostMapping("/{id}/balance-update-by-transaction")
    public ResponseEntity<Account> updateAccountBalanceByTransactionType(@PathVariable("id") Long id,
                                                                         @RequestBody Transaction transaction) {
        return ResponseEntity.accepted().body(accountService.updateAccountBalanceByTransactionType(id, transaction));
    }



}
