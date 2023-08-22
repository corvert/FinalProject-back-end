package com.example.FinalProject.controller;

import com.example.FinalProject.model.Transaction;
import com.example.FinalProject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getFullTransactionList() {
        return transactionService.getFullTransactionList();
    }

    @PostMapping("/add-transaction")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
        transactionService.save(transaction);
        return ResponseEntity.ok(transaction);
    }
}
