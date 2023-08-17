package com.example.FinalProject.repository;

import com.example.FinalProject.model.Stock;
import com.example.FinalProject.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findStockBySymbol(String symbol);
    List<Stock> findAllByAccountId(Long accountId);

}
