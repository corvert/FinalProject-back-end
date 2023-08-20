package com.example.FinalProject.service;

import com.example.FinalProject.model.Account;
import com.example.FinalProject.model.Stock;
import com.example.FinalProject.model.Transaction;
import com.example.FinalProject.repository.AccountRepository;
import com.example.FinalProject.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private  AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;

    }

    public List<Transaction> getFullTransactionList() {
        return transactionRepository.findAll();
    }



    public List<Transaction> getTransactionsListByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findAllByAccountId(accountId);
        return transactions.stream()
                .sorted((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()))
                .collect(Collectors.toList());
    }


    public Transaction save(Transaction transaction) {
        Transaction savedTransaction = toTransaction(transaction);
        return transactionRepository.save(transaction);
    }

    private Transaction toTransaction(Transaction transaction) {
        return Transaction.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionType(transaction.getTransactionType())
                .account(transaction.getAccount())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }

}
